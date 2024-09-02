package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository  extends JpaRepository<RefreshEntity, Long> {

    boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}