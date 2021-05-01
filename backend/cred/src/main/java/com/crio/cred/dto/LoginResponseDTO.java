package com.crio.cred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The type Login response dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private UUID userId;
    private String emailId;
    private String token;
    private String tokenType;
}
