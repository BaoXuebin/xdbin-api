package com.xdbin.service;

import com.xdbin.domain.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
interface BlogRepository extends JpaRepository<Blog, Serializable> {

    @Query("SELECT b FROM Blog b")
    List<Blog> findAllBlogsByPage(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.ifPub = 1")
    List<Blog> findPubBlogsByPage(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.blogId = :blogId AND b.ifPub = 1")
    Blog findPublicBlog(@Param("blogId") String blogId);

    @Modifying
    @Query("UPDATE Blog SET ifPub = :pub WHERE blogId = :blogId")
    void updateBlogPubByBlogId(@Param("pub") Integer pub, @Param("blogId") String blogId);

}