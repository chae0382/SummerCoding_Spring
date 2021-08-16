package com.example.summercoding_spring.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Post {
    private Long id;
    private String title;
    private String description;
}
