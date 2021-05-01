package com.crio.cred.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * The type Jwt config.
 *
 * @author harikesh.pallantla
 */
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    /**
     * Gets authorization header.
     *
     * @return the authorization header
     */
    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
