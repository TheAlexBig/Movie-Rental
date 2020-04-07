package com.brick.buster.main.component;

import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.service.auth.RoleServiceImp;
import com.brick.buster.main.service.auth.UserServiceImp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTTokenComponent implements Serializable {
    private final UserServiceImp userServiceImp;

    @Autowired
    public JWTTokenComponent(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Value(value = "${jwt.token.header}")
    private String HEADER;
    @Value(value = "${jwt.token.secret}")
    private String SECRET;
    @Value(value = "${jwt.token.prefix}")
    private String PREFIX;

    public String getIndentifierFromHttp(HttpServletRequest request){
        String token = request.getHeader(HEADER).replace(PREFIX+" ", "");
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getIdentifierFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromHttp(HttpServletRequest request) {
        String token = request.getHeader(HEADER).replace(PREFIX+" ", "");
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String identifier) {
        return doGenerateToken(identifier);
    }

    private String doGenerateToken(String identifier) {
        Date dt = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dt);
        cl.add(Calendar.DATE, 7);
        dt = cl.getTime();
        ArrayList<String> foundAuthorities = new ArrayList<String>();
        Optional<User> toGrantToken  = userServiceImp.findByUsernameOrEmail(identifier);
        toGrantToken.ifPresent(user->{
            user.getRoles().forEach(role->{
                role.getPrivileges().forEach(privilege -> {
                    foundAuthorities.add(privilege.getName());
                });
            });
        });
        foundAuthorities.add("LOGIN_PRIVILEGE");
        String token = Jwts.builder().setId("softtekJWT").setSubject(identifier)
                .claim("authorities", foundAuthorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(dt)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
        return PREFIX+" "+token;
    }
}
