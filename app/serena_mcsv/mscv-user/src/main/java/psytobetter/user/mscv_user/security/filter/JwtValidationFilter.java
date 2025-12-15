package psytobetter.user.mscv_user.security.filter;

import jakarta.servlet.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import psytobetter.user.mscv_user.security.JwtUtil;

import java.io.IOException;


public record JwtValidationFilter(JwtUtil jwtUtil) implements Filter {

    @Override
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, null);

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(req, res);
    }
}
