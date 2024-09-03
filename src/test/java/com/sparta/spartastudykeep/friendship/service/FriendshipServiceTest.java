package com.sparta.spartastudykeep.friendship.service;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.service.FriendshipService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FriendshipServiceTest {

    @Autowired
    private UserRepository ur;
    @Autowired
    private FriendshipService fs;
    @Autowired
    private FriendShipRepository fr;


    @Test
    @Rollback(false)
    public void 친구신청() throws Exception {
        User user1 = new User();
        user1.setUsername("테스트1");
        User user2 = new User();
        user2.setUsername("테스트2");

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, save2.getId());

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterIdAndReceiverId(save1.getId(),
            save2.getId());

        // 친구 신청을 했으면 WAITING 상태여야 함
        Assertions.assertThat(byUserIdAndFriendId).isPresent();
        Assertions.assertThat(byUserIdAndFriendId.get().getStatus()).isEqualTo(FriendShipStatus.WAITING);
    }

    @Test
    @Rollback(false)
    public void 친구등록 () throws Exception {
        // given
        User user1 = new User();
        user1.setUsername("테스트1");
        User user2 = new User();
        user2.setUsername("테스트2");

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, save2.getId());
        // user2가 -> user1의 친구 신청 수락
        fs.acceptFriendShip(save2, save1.getId());

        // then
        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterIdAndReceiverId(save2.getId(),
            save1.getId());


        Optional<Friendship> byUserIdAndFriendId1 = fr.findByRequesterIdAndReceiverId(save1.getId(),
            save2.getId());

        // 요청을 수락했으면 서로 연결되어있어야함
        Assertions.assertThat(byUserIdAndFriendId).isPresent();
        Assertions.assertThat(byUserIdAndFriendId.get().getStatus()).isEqualTo(FriendShipStatus.ACCEPTED);

        Assertions.assertThat(byUserIdAndFriendId1).isPresent();
        Assertions.assertThat(byUserIdAndFriendId1.get().getStatus()).isEqualTo(FriendShipStatus.ACCEPTED);
    }

    @Test
    public void t(){
        User user = ur.findById(1L)
            .get();
        User user2 = ur.findById(2L)
            .get();

        Optional<Friendship> first = user.getFriends()
            .stream()
            .filter(x -> x.getReceiver()
                .equals(user2))
            .findFirst();

        Optional<Friendship> second = user2.getFriends()
            .stream()
            .filter(x -> x.getReceiver()
                .equals(user))
            .findFirst();
        // 요청을 수락했으면 서로 친구목록에 있어야함
        Assertions.assertThat(first).isPresent();
        Assertions.assertThat(second).isPresent();
    }
    @Test
    public void 친구신청거부 () throws Exception {
        // given
        User user1 = new User();
        user1.setUsername("테스트1");
        User user2 = new User();
        user2.setUsername("테스트2");

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, save2.getId());
        // user2가 -> user1의 친구 신청 거부
        fs.rejectFriendshipRequest(save2, save1.getId());

        // then

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterIdAndReceiverId(save1.getId(),
            save2.getId());

        // friendship 테이블에 save1이 save2에게 친구 요청 기록이 있으면 안됨
        Assertions.assertThat(byUserIdAndFriendId).isEmpty();
    }

    @Test
    public void 친구신청거부2 () throws Exception {
        // given
        User user1 = new User();
        user1.setUsername("테스트1");
        User user2 = new User();
        user2.setUsername("테스트2");

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, save2.getId());
        // user2가 -> user1의 친구 신청 거부
        // fs.rejectFriendshipRequest(save2.getId(), save1.getId());

        // then

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterIdAndReceiverId(save1.getId(),
            save2.getId());

        // 거부를 안했으므로
        // friendship 테이블에 save1이 save2에게 친구 요청 기록이 있어야 됨
        Assertions.assertThat(byUserIdAndFriendId).isPresent();
    }
}