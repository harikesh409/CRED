package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type Sign up dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
public class SignUpDTO {
    @Email(message = "Enter a valid email address.")
    @NotBlank(message = "Email id is mandatory.")
    private String emailId;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    @NotBlank(message = "First name is mandatory.")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Mobile number is mandatory.")
    private String mobileNumber;
}
