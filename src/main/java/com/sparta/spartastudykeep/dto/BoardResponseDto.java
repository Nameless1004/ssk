package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String board_title;
    private final String board_contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final User user;
    private final String user_name;

    public BoardResponseDto(Board board, User user, String user_name ) {
        this.id = board.getId();
        this.board_title = board.getBoard_title();
        this.board_contents = board.getBoard_contents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.user = user;
        this.user_name = board.getUser_name();
    }
}

