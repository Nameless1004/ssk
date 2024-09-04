package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersResponseDto {

    private long id;
    private String username;
    private String description;

    public UsersResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.description = user.getDescription();
    }
}
