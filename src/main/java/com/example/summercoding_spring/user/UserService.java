package com.example.summercoding_spring.user;

import com.example.summercoding_spring.user.form.SignUpForm;
import com.example.summercoding_spring.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.hibernate.usertype.UserType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void update(Long userId, UserForm userForm){
        User user = findById(userId);
        user.setName(userForm.getName());
        user.setType(userForm.getType());
    }

    public User createUser(@Valid SignUpForm signUpForm) {
        String encodedPassword = passwordEncoder.encode(signUpForm.getPassword());
        User user = User.builder()
                .username(signUpForm.getUsername())
                .name(signUpForm.getName())
                .password(encodedPassword)
                .type("ROLE_USER")
                .build();

        return userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new UserAccount(user);
    }

    public void login(User user, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(user),
                passwordEncoder.encode(password),
                List.of(new SimpleGrantedAuthority(user.getType())));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
