package com.sparta.spartastudykeep.board.repository;

import com.sparta.spartastudykeep.common.exception.InvalidIdException;
import com.sparta.spartastudykeep.board.entity.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByUserIdIn(List<Long> userId, Pageable pageable);

    List<Board> findAllByUserIdIn(List<Long> userId);

    default void notFindBoard(Long boardId){
        findById(boardId).orElseThrow(() -> new InvalidIdException("ERROR!! 해당 게시글을 찾을 수 없습니다."));
    }
}
