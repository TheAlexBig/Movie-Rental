package com.brick.buster.main;

import com.brick.buster.main.repository.auth.TokenRepository;
import com.brick.buster.main.service.auth.UserDetailsServiceImp;
import com.brick.buster.main.util.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value(value = "${jwt.token.header}")
    private String HEADER;
    @Value(value = "${jwt.token.secret}")
    private String SECRET;
    @Value(value = "${jwt.token.prefix}")
    private String PREFIX;

    private final TokenRepository tokenRepository;
    private final UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImp userDetailsServiceImp, TokenRepository tokenRepository) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsServiceImp).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .addFilterBefore(new JWTAuthorizationFilter(HEADER, SECRET, PREFIX, tokenRepository),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**", "/api/v1/public/**").permitAll()
                .antMatchers("/api/v1/common/**").hasAuthority("READ_PRIVILEGE")
                .antMatchers("/api/v1/admin/**").hasAnyAuthority("WRITE_PRIVILEGE")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

}
