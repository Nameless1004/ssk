package com.sparta.spartastudykeep.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendRequestDto {

    @NotNull
    private Long receiverId;
}
