package com.sparta.spartastudykeep.bookmark.repository;

import com.sparta.spartastudykeep.board.entity.Board;
import com.sparta.spartastudykeep.bookmark.entity.Bookmark;
import com.sparta.spartastudykeep.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteAllByUser(User user);

    void deleteByUserAndBoard(User user, Board board);

    void deleteAllByUserAndBoard(User user, Board board);

    boolean existsByUser(User user);

    Page<Bookmark> findAllByUser(User user, Pageable pageable);
}
