package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String user_name;
    private final String board_title;
    private final String board_contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.user_name = board.getUser_name();
        this.board_title = board.getBoard_title();
        this.board_contents = board.getBoard_contents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}

