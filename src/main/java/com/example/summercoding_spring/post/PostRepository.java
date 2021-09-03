package com.example.summercoding_spring.post;


import com.example.summercoding_spring.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
        boolean existsByTitle(String title);
}