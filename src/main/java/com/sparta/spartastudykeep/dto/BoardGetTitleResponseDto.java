package com.sparta.spartastudykeep.dto;

import lombok.Getter;

@Getter
public class BoardGetTitleResponseDto {

    private final String board_title;

    public BoardGetTitleResponseDto(String board_title) {
        this.board_title = board_title;
    }
}
