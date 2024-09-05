package com.sparta.spartastudykeep.friendship.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.friend.dto.FriendRequestDto;
import com.sparta.spartastudykeep.friend.entity.Friendship;
import com.sparta.spartastudykeep.user.entity.User;
import com.sparta.spartastudykeep.board.repository.BoardRepository;
import com.sparta.spartastudykeep.friend.repository.FriendShipRepository;
import com.sparta.spartastudykeep.user.repository.UserRepository;
import com.sparta.spartastudykeep.friend.service.FriendshipService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private FriendShipRepository friendShipRepository;
    @Autowired
    private BoardRepository br;
    @Autowired
    private PasswordEncoder pe;

    @Test
    @Rollback(false)
    public void 친구신청() throws Exception {
        String p = pe.encode("asdasd!@#asA");
        User user1 = new User("test1", "adsfa@.com", p, "asdf", true, UserRole.USER);
        User user2 = new User("test2", "adsfa1@.com", p, "asdf", true, UserRole.USER);

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, new FriendRequestDto(save2.getId()));

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterAndReceiver(save1,
            save2);

        // 친구 신청을 했으면 WAITING 상태여야 함
        Assertions.assertThat(byUserIdAndFriendId)
            .isPresent();
        Assertions.assertThat(byUserIdAndFriendId.get()
                .getStatus())
            .isEqualTo(FriendShipStatus.WAITING);
    }

    @Test
    @Rollback(false)
    public void 친구등록() throws Exception {
        String p = pe.encode("asdasd!@#asA");
        User user1 = new User("test1", "adsfa@.com", p, "asdf", true, UserRole.USER);
        User user2 = new User("test2", "adsfa1@.com", p, "asdf", true, UserRole.USER);

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, new FriendRequestDto(save2.getId()));
        // user2가 -> user1의 친구 신청 수락
        fs.acceptFriendShip(save2, save1.getId());

        // then
        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterAndReceiver(save2,
            save1);

        Optional<Friendship> byUserIdAndFriendId1 = fr.findByRequesterAndReceiver(save1,
            save2);

        // 요청을 수락했으면 서로 연결되어있어야함
        Assertions.assertThat(byUserIdAndFriendId)
            .isPresent();
        Assertions.assertThat(byUserIdAndFriendId.get()
                .getStatus())
            .isEqualTo(FriendShipStatus.ACCEPTED);

        Assertions.assertThat(byUserIdAndFriendId1)
            .isPresent();
        Assertions.assertThat(byUserIdAndFriendId1.get()
                .getStatus())
            .isEqualTo(FriendShipStatus.ACCEPTED);
    }

    @Test
    public void t() {
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
        Assertions.assertThat(first)
            .isPresent();
        Assertions.assertThat(second)
            .isPresent();
    }

    @Test
    public void 친구신청거부() throws Exception {
        String p = pe.encode("asdasd!@#asA");
        User user1 = new User("test1", "adsfa@.com", p, "asdf", true, UserRole.USER);
        User user2 = new User("test2", "adsfa1@.com", p, "asdf", true, UserRole.USER);

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, new FriendRequestDto(save2.getId()));
        // user2가 -> user1의 친구 신청 거부
        fs.rejectFriendshipRequest(save2, save1.getId());

        // then

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterAndReceiver(save1,
            save2);

        // friendship 테이블에 save1이 save2에게 친구 요청 기록이 있으면 안됨
        Assertions.assertThat(byUserIdAndFriendId)
            .isEmpty();
    }

    @Test
    public void 친구신청거부2() throws Exception {
        // given
        String p = pe.encode("asdasd!@#asA");
        User user1 = new User("test1", "adsfa@.com", p, "asdf", true, UserRole.USER);
        User user2 = new User("test2", "adsfa1@.com", p, "asdf", true, UserRole.USER);

        User save1 = ur.save(user1);
        User save2 = ur.save(user2);

        // when

        // user1 -> user2에게 친구 신청
        fs.requestFriendship(save1, new FriendRequestDto(save2.getId()));
        // user2가 -> user1의 친구 신청 거부
        // fs.rejectFriendshipRequest(save2.getId(), save1.getId());

        // then

        Optional<Friendship> byUserIdAndFriendId = fr.findByRequesterAndReceiver(save1,
            save2);

        // 거부를 안했으므로
        // friendship 테이블에 save1이 save2에게 친구 요청 기록이 있어야 됨
        Assertions.assertThat(byUserIdAndFriendId)
            .isPresent();
    }

    @Test
    public void 친구전체삭제() throws Exception {
        User user = userRepository.findById(1L)
            .get();
        friendshipService.removeAllFriendship(user);
    }

}