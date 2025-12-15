package psytobetter.user.mscv_user.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import psytobetter.user.mscv_user.dto.Auth.LoginRequest;
import psytobetter.user.mscv_user.security.JwtUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            LoginRequest login = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            return authManager.authenticate(authToken);

        } catch (Exception e) {
            throw new RuntimeException("Error processing login");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {

        String username = auth.getName();

        String access = jwtUtil.generateAccessToken(username);
        String refresh = jwtUtil.generateRefreshToken(username);

        try {
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"accessToken\": \"" + access + "\", \"refreshToken\": \"" + refresh + "\"}"
            );
        } catch (Exception ignored) {}
    }
}
