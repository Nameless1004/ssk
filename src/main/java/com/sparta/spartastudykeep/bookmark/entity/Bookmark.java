package com.sparta.spartastudykeep.bookmark.entity;

import com.sparta.spartastudykeep.board.entity.Board;
import com.sparta.spartastudykeep.common.entity.Timestamped;
import com.sparta.spartastudykeep.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bookmarks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Bookmark(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
