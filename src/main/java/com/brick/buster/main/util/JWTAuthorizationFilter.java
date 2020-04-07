package com.brick.buster.main.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private String HEADER;
    private String SECRET;
    private String PREFIX;

    public JWTAuthorizationFilter(String HEADER, String SECRET, String PREFIX) {
        this.HEADER = HEADER;
        this.SECRET = SECRET;
        this.PREFIX = PREFIX;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if((httpServletRequest.getRequestURI().contains("/api/v1/auth") ||
                httpServletRequest.getRequestURI().contains("/api/v1/public")) &&
                        httpServletRequest.getMethod().equals(HttpMethod.POST.name()) ){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else{
            try {
                if (existingJWTToken(httpServletRequest, httpServletResponse)) {
                    Claims claims = validateToken(httpServletRequest);
                    if (claims.get("authorities") != null) {
                        setUpSpringAuthentication(claims);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                }
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                System.out.println(e.getStackTrace());
            } catch (Exception e){
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX+" ", "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        Object authorities = claims.get("authorities");
        List<?> stringList = (List<?>) authorities;
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null, stringList.stream().map(it ->
                new SimpleGrantedAuthority(it.toString()))
                .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Boolean existingJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX+" "));
    }



}
