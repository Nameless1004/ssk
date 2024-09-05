package com.sparta.spartastudykeep.user.dto;

import com.sparta.spartastudykeep.user.entity.User;
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
