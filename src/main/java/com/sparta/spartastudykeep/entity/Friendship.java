package com.sparta.spartastudykeep.entity;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Friendship {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 친구 요청을 한 사용자

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend; // 친구 요청을 받은 사용자

    @Enumerated(EnumType.STRING)
    private FriendShipStatus status;

    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
        status = FriendShipStatus.WAITING;
    }

    public void accept(){
        status = FriendShipStatus.ACCEPTED;
    }

    // Getter, Setter 및 기타 메서드들
}
