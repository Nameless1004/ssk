package com.sparta.spartastudykeep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Board extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 게시글을 식별할 수 있는 ID
//    private Long user_id;
    @Column(nullable = false)
    private String user_name;
    private String board_title;
    private String board_contents;

}
