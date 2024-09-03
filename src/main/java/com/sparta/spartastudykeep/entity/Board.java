package com.sparta.spartastudykeep.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 게시글을 식별할 수 있는 ID
//    private Long user_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    @Column(nullable = false)
    private String user_name;
    private String board_title;
    private String board_contents;

    public Board(User user, String un, String bt, String bc){
        this.user = user;
        user_name = un;
        board_title = bt;
        board_contents = bc;
    }
}
