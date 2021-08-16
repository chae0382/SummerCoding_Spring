package com.example.summercoding_spring.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class User {
    private Long id;
    private String name;
    private String type;
}
