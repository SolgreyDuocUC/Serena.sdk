package duoc.projects.serena.controller;

import duoc.projects.serena.model.User;

import duoc.projects.serena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

}

