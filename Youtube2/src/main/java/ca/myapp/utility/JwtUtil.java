package ca.myapp.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {



    private String secret = "v3ryS#cur3K3y!2024$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public String generateToken(String username) {
        System.out.println("Generating token for user: " + username);

        // Current time and expiration
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 1000 * 60 * 60 * 10); // 10 hours

        System.out.println("Issued at: " + now);
        System.out.println("Expiration time: " + exp);

        // Debugging before token generation
        System.out.println("Debugging token generation:");
        System.out.println("Username: " + username);
        System.out.println("Issued At: " + now);
        System.out.println("Expiration: " + exp);
        // Be cautious with logging the secret in a real environment
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());

        try {
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                    .compact();

            System.out.println("Token generated: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("Error generating token: " + e.getMessage());
            e.printStackTrace(); // Consider a more secure logging approach for production
            return null;
        }
    }

    public Boolean validateToken(String token, String username) {
        final String usernameInToken = getUsernameFromToken(token);
        return (usernameInToken.equals(username) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
