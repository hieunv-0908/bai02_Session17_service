package re.bai02_session17_service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Minimal JwtTokenProvider used for the exercise.
 *
 * NOTE: This is a simplified implementation for demonstration and testing purposes only.
 * In a real application you should parse and validate JWT signatures, expiration, etc.
 */
@Component
public class JwtTokenProvider {

    /**
     * Extract username from token. For this exercise we treat the whole token as the username.
     * Replace with real JWT parsing in production.
     */
    public String getUsernameFromJWT(String token) {
        if (token == null || token.isEmpty()) return null;
        // In the training material the provider would parse the JWT and return the subject (username)
        return token;
    }

    /**
     * Validate token against the provided UserDetails.
     * The method signature matches the sample from the lesson: validateToken(String, UserDetails)
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;
        String username = getUsernameFromJWT(token);
        return username != null && username.equals(userDetails.getUsername());
    }
}

