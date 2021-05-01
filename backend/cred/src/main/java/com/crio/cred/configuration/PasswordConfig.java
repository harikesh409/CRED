package com.crio.cred.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Password config.
 *
 * @author harikesh.pallantla
 */
@Configuration
public class PasswordConfig {
    /**
     * Password encoder registers a BCryptPasswordEncoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }
}
