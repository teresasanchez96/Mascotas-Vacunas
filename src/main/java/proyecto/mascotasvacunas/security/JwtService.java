package proyecto.mascotasvacunas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/*
 * Esta clase se encarga de crear y validar los tokens JWT.
 * El token permite identificar al usuario en las peticiones que realiza a la API.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    // Obtiene la clave utilizada para firmar y validar los tokens.
    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Genera un token JWT para el usuario utilizando su email.
    public String generarToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                )
                .signWith(getSecretKey())
                .compact();
    }

    // Extrae el email del usuario que está incluido en el token.
    public String extraerEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Comprueba si el token es válido.
    public boolean esTokenValido(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}