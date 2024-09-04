package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.Bookmark;
import com.sparta.spartastudykeep.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteAllByUser(User user);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

    void deleteAllByUserAndBoard(User user, Board board);

    boolean existsByUser(User user);

    Page<Bookmark> findAllByUser(User user, Pageable pageable);
}
