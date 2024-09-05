package com.sparta.spartastudykeep.friend.entity;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Friendship {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester; // 요청을 한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver; // 요청을 받은 사용자

    @Enumerated(EnumType.STRING)
    private FriendShipStatus status;

    public Friendship(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
        status = FriendShipStatus.WAITING;
    }

    public void accept() {
        status = FriendShipStatus.ACCEPTED;
    }

    // Getter, Setter 및 기타 메서드들
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Friendship) {
            Friendship other = (Friendship) obj;
            return requester.equals(other.receiver) && receiver.equals(other.requester) && status == other.status && status == FriendShipStatus.ACCEPTED;
        }

        return false;
    }
}
