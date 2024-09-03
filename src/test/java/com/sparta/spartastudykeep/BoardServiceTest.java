package com.sparta.spartastudykeep;

import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.dto.FriendRequestDto;
import com.sparta.spartastudykeep.dto.FriendResponseDto;
import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.BoardRepository;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.service.BookmarkService;
import com.sparta.spartastudykeep.service.FriendshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired  private UserRepository ur;
    @Autowired private FriendshipService fs;
    @Autowired  private FriendShipRepository fr;
    @Autowired  private BoardRepository br;
    @Autowired private PasswordEncoder pe;
    @Autowired private BookmarkService bs;

    // String username, String email, String password, String description, Boolean enabled,
    //        UserRole role
    @Test
    @Rollback(false)
    public void Test(){

        String p = pe.encode("123");
        User user = new User("test1", "adsfa@.com", p, "asdf", true, UserRole.USER);
        User user1 = new User("test2", "adsfa1@.com", p, "asdf", true, UserRole.USER);
        User user2 = new User("test3", "adsfaa2@.com", p, "asdf", true, UserRole.USER);
        User user3 = new User("test4", "adsafa3@.com", p, "asdf", true, UserRole.USER);
        User user4 = new User("test5", "adsafa4@.com", p, "asdf", true, UserRole.USER);
        User user5 = new User("zxcv", "zxcv5@.com", p,"zxcv",true, UserRole.USER);
        User user6 = new User("zxcb", "zxcb6@.com", p,"zxcd",true, UserRole.USER);

       ur.save(user);
       ur.save(user1);
       ur.save(user2);
       ur.save(user3);
       ur.save(user4);
       ur.save(user5);
       ur.save(user6);

        fs.requestFriendship(user, new FriendRequestDto(user1.getId()));
        fs.requestFriendship(user, new FriendRequestDto(user2.getId()));
        fs.requestFriendship(user, new FriendRequestDto(user3.getId()));
        fs.requestFriendship(user, new FriendRequestDto(user4.getId()));
        fs.requestFriendship(user, new FriendRequestDto(user5.getId()));
        fr.flush();
        fs.acceptFriendShip(user1, user.getId());
        fs.acceptFriendShip(user2, user.getId());
        fs.acceptFriendShip(user3, user.getId());
        fs.acceptFriendShip(user4, user.getId());
        fr.flush();

        List<Board> b = new ArrayList<>();
        for(int i = 0; i < 30; ++i){
            int a = 100 + (int)(Math.random() * 500000);
            if(i % 4 == 0) {
                b.add(new Board(user1, user1.getUsername(), "테스트 타이틀 1" + a, "테스트 내용입니다."));
            } else if(i % 4 == 1){
                b.add(new Board(user2, user2.getUsername(), "테스트 타이틀 2" + a, "테스트 내용입니다."));
            }
            else if(i % 4 == 2){
                b.add(new Board(user3, user3.getUsername(), "테스트 타이틀 3" + a, "테스트 내용입니다."));
            }else {
                b.add(new Board(user3, user4.getUsername(), "테스트 타이틀 4" + a, "테스트 내용입니다."));
            }
        }

        br.saveAll(b);
        br.flush();

        bs.addBookmark(user1, b.get(2).getId());
        bs.addBookmark(user1, b.get(3).getId());
        bs.addBookmark(user1, b.get(4).getId());
        bs.addBookmark(user1, b.get(5).getId());
        bs.addBookmark(user1, b.get(6).getId());
    }

    @Test
    public void test2(){
        User user = ur.findById(1L).get();
        List<FriendResponseDto> friends = fs.getFriendAll(user);
        System.out.println(friends.size());
        List<Long> ids = friends.stream().map(FriendResponseDto::getUserId).toList();
        for(Long id : ids)
        {
            System.out.println("id = " + id);
        }

        List<Board> board = br.findAllByUserIdIn(ids);
        System.out.println(board.size());
        for(Board b : board){
            System.out.println(b.getBoard_title());
        }
    }
}
