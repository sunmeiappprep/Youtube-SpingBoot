package ca.myapp.utility;

import ca.myapp.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {



    private String secret = "8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fb";

    public String generateToken(String username) {
        System.out.println("Generating token for user: " + username);
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
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

        try {
            // Directly use the secret.getBytes(StandardCharsets.UTF_8) without Base64 encoding
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, keyBytes)
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
        try {
            final String usernameInToken = getUsernameFromToken(token);
            return (usernameInToken.equals(username) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired");
        } catch (JwtException | IllegalArgumentException e) {
            // Handle other JWT exceptions
            throw new RuntimeException("Token validation failed");
        }
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
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
