package com.crio.cred.security;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * The type Jwt secret key.
 *
 * @author harikesh.pallantla
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtSecretKey {
    private final JwtConfig jwtConfig;

    /**
     * Secret key secret key.
     *
     * @return the secret key
     */
    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
