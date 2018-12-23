package com.xdbin.comment.repository;

import com.xdbin.comment.entity.Comment;
import com.xdbin.common.repository.NativeQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: BaoXuebin
 * Date: 2018/9/13
 * Time: 下午11:10
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, NativeQueryRepository {

    Comment findById(Long id);

    Page<Comment> findAllByOrigin(String origin, Pageable pageable);

    Page<Comment> findAll(Pageable pageable);

}
