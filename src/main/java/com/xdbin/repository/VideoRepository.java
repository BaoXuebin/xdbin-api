package com.xdbin.repository;

import com.xdbin.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午3:24
 */
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT v FROM Video v WHERE v.pub = 1")
    Page<Video> findVideosByPub(Pageable pageable);

}
