package com.crio.cred.security;

import com.crio.cred.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

/**
 * The type Web security config.
 *
 * @author harikesh.pallantla
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    // List of URLs that do not need authentication.
    private final String[] whiteListedURLs = {
            "/",
            "/login",
            "/signup",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/h2-console/**",
            "/actuator/**",
            "/favicon.ico"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.trace("Entered configure httpSecurity");
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // Making the spring security session stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Filter to verify the token.
                .addFilterBefore(new JwtTokenVerifier(secretKey, jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // dont authenticate the list of URLs
                .antMatchers(whiteListedURLs).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
        logger.trace("Exited configure httpSecurity");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.trace("Entered configure authenticationMangerBuilder");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        logger.trace("Exited configure authenticationMangerBuilder");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
