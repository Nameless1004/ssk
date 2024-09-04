package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String board_title;
    private String board_contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private String user_name;

    public BoardResponseDto(Board board, User user) {
        this.id = board.getId();
        this.board_title = board.getBoard_title();
        this.board_contents = board.getBoard_contents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.userId = user.getId();
        this.user_name = user.getUsername();
    }
}

