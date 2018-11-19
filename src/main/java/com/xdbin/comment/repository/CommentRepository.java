package com.xdbin.comment.repository;

import com.xdbin.comment.entity.Comment;
import com.xdbin.common.repository.NativeRepository;
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
public interface CommentRepository extends JpaRepository<Comment, Long>, NativeRepository {

    Comment findById(Long id);

    Page<Comment> findAllByOrigin(String origin, Pageable pageable);

    Page<Comment> findAll(Pageable pageable);

}
