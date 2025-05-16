package com.example.twentyfiveframes.domain.user.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserType role;

    public User(String email, String password, String username, UserType role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }
}
