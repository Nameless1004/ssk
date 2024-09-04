package com.sparta.spartastudykeep.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FriendshipReceiveDto {

    private Long friendshipId;
    private Long requesterId;
    private Long receiverId;
}
