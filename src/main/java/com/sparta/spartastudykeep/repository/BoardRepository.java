package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByUserIdIn(List<Long> userId, Pageable pageable);

    List<Board> findAllByUserIdIn(List<Long> userId);
}
