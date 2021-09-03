package com.example.summercoding_spring.post;

import com.example.summercoding_spring.comment.Comment;
import com.example.summercoding_spring.comment.CommentService;
import com.example.summercoding_spring.comment.form.CommentForm;
import com.example.summercoding_spring.post.form.PostForm;
import com.example.summercoding_spring.post.validator.PostFormValidator;
import com.example.summercoding_spring.user.CurrentUser;
import com.example.summercoding_spring.user.User;
import com.example.summercoding_spring.user.UserService;
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

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final PostFormValidator postFormValidator;

    @InitBinder("postForm")
    public void initBindPostForm(WebDataBinder webDataBinder){
        webDataBinder.addValidators(postFormValidator);

    }

    //게시글 목록 조회
    // GET /posts
    @GetMapping("/posts")
    public String index(Model model){

        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "posts/index";
    }

    //게시글 상세 조회
    @GetMapping("/posts/{postId}")
    public String show(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        model.addAttribute("commentForm", new CommentForm());
        return "posts/show";
    }

    @PostMapping("/posts/{postId}/new-comment")
    public String createComment(@CurrentUser User user,@PathVariable Long postId, CommentForm commentForm){
        Post post = postService.findById(postId);
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .user(user)
                .post(post)
                .build();
        commentService.create(comment);
        post.addComment(comment);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("posts/new-post")
    public String newPost(@Valid Model model){
        model.addAttribute("postForm", new PostForm());

        return "posts/new-post";
    }

    //게시글 생성
    // POST /new-post
    @PostMapping("/new-post")
    public String create(
        @CurrentUser User user,
        @Valid PostForm postForm,
        Errors errors
    ){
        //if(errors.hasErrors()){
        //    return "posts/new-post";
        //}
        postService.create(postForm, user);

        return "redirect:/posts";
    }

    // 게시글 수정
    // POST /posts/{post_id}
    @GetMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post", post);

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setDescription(post.getDescription());
        model.addAttribute("postForm", postForm);

        return "posts/edit-post";
    }

    @PostMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, PostForm postForm){
        postService.update(postId, postForm);
        return "redirect:/posts";
    }

}