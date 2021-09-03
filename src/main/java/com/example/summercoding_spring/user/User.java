package com.example.summercoding_spring.user;

import com.example.summercoding_spring.post.Post;
import lombok.*;
import org.hibernate.usertype.UserType;

import javax.persistence.*;
import java.util.List;


@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    private String name;

    private String type;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
