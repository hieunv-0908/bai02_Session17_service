package re.bai02_session17_service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String getUsernameFromJWT(String token) {
        if (token == null || token.isEmpty()) return null;
        return token;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;
        String username = getUsernameFromJWT(token);
        return username != null && username.equals(userDetails.getUsername());
    }
}

