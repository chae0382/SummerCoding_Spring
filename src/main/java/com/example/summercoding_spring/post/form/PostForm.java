package com.example.summercoding_spring.post.form;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostForm {
    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}

