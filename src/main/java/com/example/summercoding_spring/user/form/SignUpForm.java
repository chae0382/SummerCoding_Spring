package com.example.summercoding_spring.user.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirmation;
    @NotBlank
    private String name;
}
