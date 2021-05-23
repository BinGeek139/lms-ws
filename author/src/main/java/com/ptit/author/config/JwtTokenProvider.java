package com.ptit.author.config;

import com.ptit.author.entity.CustomUserDetails;
import com.ptit.author.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = "ngocquang139Ptitngocquang139Ptitngocquang139Ptitngocquang139Ptit";
    private final long JWT_EXPIRATION = 518400;

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        User user=userDetails.getUser();
        return Jwts.builder()
                .setSubject(user.getId()+";"+user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("ExpiredJwtException JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(Jwts.builder()
                .setSubject("402881777995726d017995730b0b0000;ROLE_ADMIN")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 10000000))
                .signWith(SignatureAlgorithm.HS256, "ngocquang139Ptitngocquang139Ptitngocquang139Ptitngocquang139Ptit")
                .compact());
    }
}
