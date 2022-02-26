package infrastructure.security;

import infrastructure.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + 1000L * jwtProperties.getTokenExpirationSec());
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userPrincipal.getEmail());
        return Jwts.builder().addClaims(claims).setExpiration(expiredTime)
            .signWith(SignatureAlgorithm.HS512,
                jwtProperties.getTokenSecret().getBytes(StandardCharsets.UTF_8)).compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtProperties.getTokenSecret().getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token).getBody();
        return (String) claims.get("email");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getTokenSecret().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return bearerToken;
        }
        bearerToken = request.getHeader(jwtProperties.getTokenKey());
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }
}
