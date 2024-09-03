package com.sparta.spartastudykeep.dto;

import lombok.Getter;

@Getter
public class BoardGetTitleResponseDto {

    private final String title;

    public BoardGetTitleResponseDto(String title) {
        this.title = title;
    }
}
