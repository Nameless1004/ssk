package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.bookmark.service.BookmarkService;
import com.sparta.spartastudykeep.bookmark.entity.Bookmark;
import com.sparta.spartastudykeep.user.entity.User;
import com.sparta.spartastudykeep.bookmark.repository.BookmarkRepository;
import com.sparta.spartastudykeep.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookmarkServiceTest {

    @Autowired
    BookmarkService bs;
    @Autowired
    BookmarkRepository bookr;
    @Autowired
    UserRepository ur;

    @Test
    public void 북마크모두조회() {
        User user = ur.findById(2L)
            .get();

        Page<Bookmark> bookmarks = bookr.findAllByUser(user,
            PageRequest.of(0, 10, Direction.ASC, "createdAt"));
        for (Bookmark bookmark : bookmarks) {
            System.out.println(bookmark.getBoard()
                .getBoardTitle());
        }
    }

    @Test
    public void 북마크삭제() {
        User user = ur.findById(2L)
            .get();

        Page<Bookmark> bookmarks = bookr.findAllByUser(user,
            PageRequest.of(0, 100, Direction.ASC, "createdAt"));
        long cnt = bookmarks.getContent()
            .size();
        bookr.deleteById(bookmarks.getContent()
            .get(0)
            .getId());
        long afterCnt = bookr.count();
        Assertions.assertThat(afterCnt)
            .isEqualTo(cnt - 1);
    }

    @Test
    public void 북마크있는지검사() {
        // 북마크 존재하는 유저
        User user = ur.findById(2L)
            .get();

        // 북마크 없는 유저
        User user2 = ur.findById(3L)
            .get();

        boolean a = bookr.existsByUser(user);
        boolean b = bookr.existsByUser(user2);
        Assertions.assertThat(a)
            .isTrue();
        Assertions.assertThat(b)
            .isFalse();
    }

    @Test
    public void 북마크모두삭제() {
        User user = ur.findById(2L)
            .get();
        bookr.deleteAllByUser(user);
        long count = bookr.count();
        Assertions.assertThat(count)
            .isEqualTo(0);
    }
}