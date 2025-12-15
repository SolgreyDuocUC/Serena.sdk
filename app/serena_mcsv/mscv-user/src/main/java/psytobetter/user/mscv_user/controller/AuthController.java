package psytobetter.user.mscv_user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psytobetter.user.mscv_user.dto.Auth.AuthResponse;
import psytobetter.user.mscv_user.dto.Auth.LoginRequest;
import psytobetter.user.mscv_user.dto.user.UserCreateRequestDTO;
import psytobetter.user.mscv_user.dto.user.UserDTO;
import psytobetter.user.mscv_user.security.JwtUtil;
import psytobetter.user.mscv_user.service.User.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro, login y refresh token")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    //         REGISTER
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema."
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateRequestDTO dto) {
        UserDTO userDTO = userService.createUser(dto);
        return ResponseEntity.ok(userDTO);
    }

    //           LOGIN
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica con email y contraseña, devolviendo access y refresh tokens."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        UserDTO user = userService.findByEmail(request.getEmail());

        if (!userService.matchesPassword(request.getPassword(), user.getUserPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        String email = user.getUserEmail();

        String access = jwtUtil.generateAccessToken(email);
        String refresh = jwtUtil.generateRefreshToken(email);

        return ResponseEntity.ok(new AuthResponse(access, refresh));
    }

    //       REFRESH TOKEN
    @Operation(
            summary = "Refrescar el token",
            description = "Genera un nuevo access token usando un refresh token válido."
    )
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {

        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body("El refresh token es requerido.");
        }

        // Extraer email del refresh token
        String email;
        try {
            email = jwtUtil.getUsernameFromRefreshToken(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Refresh token inválido o manipulado.");
        }

        // Validar firma + expiración
        if (!jwtUtil.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token expirado.");
        }

        // Obtener usuario (opcional pero recomendado)
        UserDTO user = userService.findByEmail(email);

        // Crear nuevo access token
        String newAccess = jwtUtil.generateAccessToken(email);

        return ResponseEntity.ok(new AuthResponse(newAccess, refreshToken));
    }
}
