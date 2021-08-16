package com.example.summercoding_spring.user;

import com.example.summercoding_spring.user.form.UserForm;
import com.example.summercoding_spring.user.validator.UserFormValidator;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFormValidator userFormValidator;

    @InitBinder("userForm")
    public void initBindUserForm(WebDataBinder webDataBinder){
        webDataBinder.addValidators(userFormValidator);

    }

    // 유저 목록 조회
    // GET /users
    @GetMapping("/users")
    public String index(Model model) {
        userService.save(new User(1L, "하이", "하이"));
        userService.save(new User(2L, "하이1", "하이1"));

        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "users/index";
    }

    // 유저 상세 조회
    // GET /users/1
    @GetMapping("/users/{userId}")
    public String show(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return "users/show";
    }

    @GetMapping("users/new-user")
    public String newUser(@Valid Model model) {
        model.addAttribute("userForm", new UserForm());

        return "users/new-user";
    }

    // 유저 생성
    // POST /new-user
    @PostMapping("/new-user")
    public String create(@Valid UserForm userForm, Errors errors) {
        if (errors.hasErrors()){
            return "users/new-user";
        }
        User user = new User(
                userForm.getId(),
                userForm.getName(),
                userForm.getType()
        );
        userService.save(user);

        return "redirect:/users";
    }

    // 유저 수정
    // POST /users/{user_id}
    @GetMapping("/users/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("userForm", new UserForm(
                user.getId(),
                user.getName(),
                user.getType()
        ));

        return "users/edit-user";
    }

    @PostMapping("/users/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, UserForm userForm) {
        User user = userService.findById(userId);
        user.setId(userForm.getId());
        user.setName(userForm.getName());
        user.setType(userForm.getType());

        return "redirect:/users";
    }
}
