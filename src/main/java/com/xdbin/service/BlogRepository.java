package com.xdbin.service;

import com.xdbin.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
interface BlogRepository extends JpaRepository<Blog, Serializable> {

    List<Blog> findBlogsByIfPubEquals(int ifPub);

    @Query("SELECT b FROM Blog b WHERE b.blogId = :blogId AND b.ifPub = 1")
    Blog findPublicBlog(@Param("blogId") String blogId);

}
