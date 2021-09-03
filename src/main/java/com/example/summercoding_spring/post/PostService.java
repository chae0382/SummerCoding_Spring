package com.example.summercoding_spring.post;

import com.example.summercoding_spring.post.form.PostForm;
import com.example.summercoding_spring.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void create(PostForm postForm, User user) {
        Post post = Post.builder()
                .title(postForm.getTitle())
                .description(postForm.getDescription())
                .user(user)
                .build();
        save(post);
    }

    @Transactional
    public void update(Long postId, PostForm postForm) {
        Post post = findById(postId);

        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
    }
}