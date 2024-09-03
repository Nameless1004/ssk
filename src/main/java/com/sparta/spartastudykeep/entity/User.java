package com.sparta.spartastudykeep.entity;


import com.sparta.spartastudykeep.common.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // 내가 요청한 친구 목록
    @OneToMany(mappedBy = "requester")
    private List<Friendship> friends = new ArrayList<>();

    public User(String username, String email, String password, String description, Boolean enabled,
        UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
        this.enabled = enabled;
        this.role = role;
    }
}
