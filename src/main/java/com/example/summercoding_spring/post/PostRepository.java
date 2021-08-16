package com.example.summercoding_spring.post;


import com.example.summercoding_spring.post.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class PostRepository {

    private final HashMap<Long, Post> posts = new HashMap<>();

    public Post findById(Long id) {
        return posts.get(id);
    }

    public Boolean existsById(Long id){
        return posts.get(id) != null;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public void save(Post post) {
        posts.put(post.getId(), post);
    }

    public boolean existsByTitle(String title){
        return posts.values().stream().anyMatch(post -> post.getTitle().equals(title));
    }
}
