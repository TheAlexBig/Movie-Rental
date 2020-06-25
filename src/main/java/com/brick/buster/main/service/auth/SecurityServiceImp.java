package com.brick.buster.main.service.auth;

import com.brick.buster.main.service.auth.interfaces.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImp implements SecurityService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImp userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(SecurityServiceImp.class);

    @Autowired
    public SecurityServiceImp(AuthenticationManager authenticationManager, UserDetailsServiceImp userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public String findLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String identifier = auth.getName();
        Object details = auth.getDetails();
        if(details instanceof WebAuthenticationDetails){
            return identifier;
        }
        else return "not-found";
    }

    @Override
    public void autoLogin(String identifier, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(identifier);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        if(authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            logger.debug(String.format("Auto login %s successfully!", identifier));
        }
    }

}
