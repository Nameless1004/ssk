package com.sparta.spartastudykeep.board.dto;

import com.sparta.spartastudykeep.board.entity.Board;
import com.sparta.spartastudykeep.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private String userName;

    public BoardResponseDto(Board board, User user) {
        this.id = board.getId();
        this.boardTitle = board.getBoardTitle();
        this.boardContents = board.getBoardContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.userId = user.getId();
        this.userName = user.getUsername();
    }
}

