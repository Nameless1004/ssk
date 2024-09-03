package com.sparta.spartastudykeep.friendship;

import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FriendTest {

    @Autowired
    private UserRepository em;

    @Test
    @Rollback(false)
    public void 친구등록테스트() throws Exception {
        // given
        User user1 = new User();
        user1.setUsername("테스트1");
        User user2 = new User();
        user2.setUsername("테스트2");
        User user3 = new User();
        user3.setUsername("테스트3");

        user1.addFriend(user2);
        user1.addFriend(user3);
        user2.accept(user1);
        user3.accept(user1);
        em.save(user1);
        em.save(user2);
        em.save(user3);
        // 궁금한점 친구 수락을 받으면 그 때 친구 테이블에 두개가 생겨야 하나?
        User user = em.findById(1L)
            .get();
        em.flush();

        List<Friendship> friends = user.getFriends();
        for(Friendship friendship : friends) {
            System.out.println("friendship = " + friendship.getReceiver().getUsername());
        }

        Assertions.assertThat(friends.get(0).getReceiver()).isSameAs(em.findById(2L).get());
        // then
    }
}
