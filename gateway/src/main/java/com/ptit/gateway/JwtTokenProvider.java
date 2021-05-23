package com.ptit.gateway;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = "ngocquang139Ptitngocquang139Ptitngocquang139Ptitngocquang139Ptit";
    private final long JWT_EXPIRATION = 518400;


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

    public String getSubjectFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        String data=claims.getSubject();
        String id=data.split(";")[0];
        String role=data.split(";")[1];
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
        User principal =
        new User(id, "",true,true,true,true,authorities);


        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


}
