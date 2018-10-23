package com.xdbin.service;

import com.xdbin.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
interface BlogRepository extends JpaRepository<Blog, Serializable>, JpaSpecificationExecutor<Blog> {

    @Override
    Page<Blog> findAll(Specification<Blog> specification, Pageable pageable);

//    @Query("SELECT b FROM Blog b")
//    List<Blog> findAllBlogsByPage(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.ifPub = 1")
    List<Blog> findPubBlogsByPage(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.blogId = :blogId AND b.ifPub = 1")
    Blog findPublicBlog(@Param("blogId") String blogId);

    // 根据标签 id 查询博客列表
    @Query("SELECT b FROM Blog b WHERE b.tags like CONCAT('%', :tagId, '%') AND b.ifPub = 1")
    List<Blog> findBlogsLikeTagId(Pageable pageable, @Param("tagId") String tagId);

    // 根据博客标题，模糊查询博客内容
    @Query("SELECT b FROM Blog b WHERE b.title LIKE CONCAT('%', :title, '%') AND b.ifPub = 1")
    List<Blog> findBlogsLikeTitle(Pageable pageable, @Param("title") String title);

    @Modifying
    @Query("UPDATE Blog SET ifPub = :pub WHERE blogId = :blogId")
    void updateBlogPubByBlogId(@Param("pub") Integer pub, @Param("blogId") String blogId);

}
