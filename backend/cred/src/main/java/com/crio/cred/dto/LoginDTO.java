package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type Login dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
public class LoginDTO {
    @Email(message = "Enter a valid email address.")
    @NotBlank(message = "Email id is mandatory.")
    private String emailId;
    @NotBlank(message = "Password is mandatory.")
    private String password;
}
