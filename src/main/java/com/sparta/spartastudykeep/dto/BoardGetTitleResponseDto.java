package com.sparta.spartastudykeep.dto;

import lombok.Getter;

@Getter
public class BoardGetTitleResponseDto {

    private final String boardTitle;

    public BoardGetTitleResponseDto(String boardTitle) {
        this.boardTitle = boardTitle;
    }
}
