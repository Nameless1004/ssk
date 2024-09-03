package com.sparta.spartastudykeep.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String title;
    private String content;

    public Board(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public void updateTitle(String title) { this.title = title;
    }

    public void updateContent(String content) { this.content = content;
    }
}
