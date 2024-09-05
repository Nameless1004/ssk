package com.sparta.spartastudykeep.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {

    @NotNull
    private Long receiverId;
}
