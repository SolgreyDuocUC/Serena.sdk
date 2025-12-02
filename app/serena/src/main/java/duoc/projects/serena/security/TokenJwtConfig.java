package duoc.projects.serena.security;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

public class TokenJwtConfig {

    // Llave generada automáticamente cada vez que levantas el servidor
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    // Prefijo estándar del token Bearer
    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    // Header donde se envía el token
    public static final String HEADER_STRING = "Authorization";

    // Tipo de contenido para las respuestas JSON
    public static final String CONTENT_TYPE = "application/json";
}
