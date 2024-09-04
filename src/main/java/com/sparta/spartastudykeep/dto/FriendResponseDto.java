package com.sparta.spartastudykeep.dto;

import com.sparta.spartastudykeep.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {

    private Long userId;
    private String userName;

    public FriendResponseDto(User user) {
        this.userId = user.getId();
        this.userName = user.getUsername();
    }
}
