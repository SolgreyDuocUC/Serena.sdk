package psytobetter.user.mscv_user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "ASDFGHJKLZXCVBNMQWERTYUIOP123456";
    private final String REFRESH_SECRET_KEY = "ZXCVBNMASDFGHJKLQWERTYUIOP987654";

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 30;       // 30 minutos
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 días

    private SecretKey getAccessKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshKey() {
        return Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    //    GENERACIÓN TOKENS

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getAccessKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getRefreshKey())
                .compact();
    }

    //    LECTURA TOKENS

    public String getUsernameFromAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(getAccessKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(getRefreshKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    //   VALIDACIONES

    public boolean isRefreshTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getRefreshKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getAccessKey())
                    .getClass()
                    .getModule()
                    .getName();
        } catch (Exception e) {
            return null;
        }
    }
}
