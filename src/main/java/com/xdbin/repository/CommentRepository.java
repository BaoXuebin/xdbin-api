package com.xdbin.repository;

import com.xdbin.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: BaoXuebin
 * Date: 2018/9/13
 * Time: 下午11:10
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.valid = 1 AND c.id = :id")
    Comment getCommentById(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.valid = 1 AND c.origin = :origin")
    Page<Comment> getCommentsByOrigin(@Param("origin") String origin, Pageable pageable);

}
