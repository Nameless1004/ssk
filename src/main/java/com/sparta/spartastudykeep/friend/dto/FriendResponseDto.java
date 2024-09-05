package com.sparta.spartastudykeep.friend.dto;

import com.sparta.spartastudykeep.user.entity.User;
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
