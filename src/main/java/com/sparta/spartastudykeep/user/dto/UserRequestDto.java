package com.sparta.spartastudykeep.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String username;
    private String password;
    private String description;
}
