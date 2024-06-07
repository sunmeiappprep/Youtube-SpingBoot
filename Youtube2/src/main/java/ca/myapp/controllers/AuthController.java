package ca.myapp.controllers;

import ca.myapp.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            // Remove "Bearer " prefix if present
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                String username = jwtUtil.getUsernameFromToken(token);
                boolean isValid = jwtUtil.validateToken(token, username);
                return ResponseEntity.ok().body(isValid);
            } else {
                return ResponseEntity.status(401).body("Invalid token format");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token is invalid or expired");
        }
    }
}
