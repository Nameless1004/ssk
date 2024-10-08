package com.sparta.spartastudykeep.user.entity;


import com.sparta.spartastudykeep.board.entity.Board;
import com.sparta.spartastudykeep.bookmark.entity.Bookmark;
import com.sparta.spartastudykeep.common.entity.Timestamped;
import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.friend.entity.Friendship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    // 내가 요청한 친구 목록
    @OneToMany(mappedBy = "requester")
    private List<Friendship> friends = new ArrayList<>();

    // 내가 작성한 글
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

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
