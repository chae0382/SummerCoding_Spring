package com.example.summercoding_spring.post.validator;

import com.example.summercoding_spring.post.PostRepository;
import com.example.summercoding_spring.post.form.PostForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PostFormValidator implements Validator {

    private final PostRepository postRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm)target;

        if(postRepository.existsByTitle(postForm.getTitle())){
            errors.rejectValue("title", "exists-value", "이미 존재하는 Title 입니다.");
        }
    }
}
