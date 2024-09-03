package com.sparta.spartastudykeep.dto;

import lombok.Getter;

@Getter
public class BoardGetDetailResponseDto {

    private final Long id;
    private final String username;
    private final String title;
    private final String content;

    public BoardGetDetailResponseDto(Long id, String username, String title, String content) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
