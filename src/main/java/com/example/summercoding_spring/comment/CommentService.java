package com.example.summercoding_spring.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Comment comment) {
        commentRepository.save(comment);
    }
}
