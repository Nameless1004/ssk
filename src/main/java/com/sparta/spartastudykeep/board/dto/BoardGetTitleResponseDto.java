package com.sparta.spartastudykeep.board.dto;

import lombok.Getter;

@Getter
public class BoardGetTitleResponseDto {

    private final String boardTitle;

    public BoardGetTitleResponseDto(String boardTitle) {
        this.boardTitle = boardTitle;
    }
}
