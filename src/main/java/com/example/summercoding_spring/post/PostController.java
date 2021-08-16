package com.example.summercoding_spring.post;

import com.example.summercoding_spring.post.form.PostForm;
import com.example.summercoding_spring.post.validator.PostFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostFormValidator postFormValidator;

    @InitBinder("userForm")
    public void initBindUserForm(WebDataBinder webDataBinder){
        webDataBinder.addValidators(postFormValidator);

    }

    //게시글 목록 조회
    // GET /posts
    @GetMapping("/posts")
    public String index(Model model){
        postService.save(new Post(1L,"첫번째 게시글", "처음입니다."));
        postService.save(new Post(2L,"두번째 게시글", "두번째입니다."));
        postService.save(new Post(3L,"세번째 게시글", "세번째입니다."));

        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "posts/index";
    }

    //게시글 상세 조회
    @GetMapping("/posts/{postId}")
    public String show(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post", post);

        return "posts/show";
    }

    @GetMapping("posts/new-post")
    public String newPost(@Valid Model model){
        model.addAttribute("postForm", new PostForm());

        return "posts/new-post";
    }

    //게시글 생성
    // POST /new-post
    @PostMapping("/new-post")
    public String create(@Valid PostForm postForm, Errors errors){
        if(errors.hasErrors()){
            return "posts/new-post";
        }
        Post post = new Post(
                postForm.getId(),
                postForm.getTitle(),
                postForm.getDescription()
        );
        postService.save(post);

        return "redirect:/posts";
    }

    // 게시글 수정
    // POST /posts/{post_id}
    @GetMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("postForm", new PostForm(
                post.getId(),
                post.getTitle(),
                post.getDescription()
        ));
        return "posts/edit-post";
    }

    @PostMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, PostForm postForm){
        Post post = postService.findById(postId);
        post.setId(postForm.getId());
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());

        return "redirect:/posts";
    }
}
