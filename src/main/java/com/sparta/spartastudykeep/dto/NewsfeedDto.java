package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsfeedDto {

    private Long boardId;
    private Long authorId;
    private String username;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public NewsfeedDto(Board board){
        this.boardId = board.getId();
        this.authorId = board.getUser().getId();
        this.username = board.getUser_name();
        this.title = board.getBoard_title();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
