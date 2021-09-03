package com.example.summercoding_spring.user.validator;

import com.example.summercoding_spring.user.UserRepository;
import com.example.summercoding_spring.user.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm)target;

        if (userRepository.existsByName(signUpForm.getUsername())) {
            errors.rejectValue("username", "exists-value", "이미 존재하는 계정입니다.");
        }

        if (!signUpForm.getPassword().equals(signUpForm.getPasswordConfirmation())) {
            errors.rejectValue("password", "exists-value", "패스워드가 일치하지 않습니다.");
        }
    }
}