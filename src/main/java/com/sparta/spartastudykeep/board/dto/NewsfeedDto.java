package com.sparta.spartastudykeep.board.dto;

import com.sparta.spartastudykeep.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NewsfeedDto {

    private final Long boardId;
    private final Long authorId;
    private final String username;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public NewsfeedDto(Board board) {
        this.boardId = board.getId();
        this.authorId = board.getUser()
            .getId();
        this.username = board.getUserName();
        this.title = board.getBoardTitle();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
