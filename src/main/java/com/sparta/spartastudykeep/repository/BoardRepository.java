package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByUserIdIn(List<Long> userId, Pageable pageable);
    List<Board> findAllByUserIdIn(List<Long> userId);
}
