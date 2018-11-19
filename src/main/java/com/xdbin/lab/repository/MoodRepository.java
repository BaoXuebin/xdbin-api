package com.xdbin.lab.repository;

import com.xdbin.lab.entity.Mood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Long> {

    Page<Mood> findAll(Pageable pageable);

}
