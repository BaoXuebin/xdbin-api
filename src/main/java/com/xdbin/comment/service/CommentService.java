package com.xdbin.comment.service;

import com.xdbin.comment.entity.Comment;
import com.xdbin.comment.model.CommentVo;
import com.xdbin.common.base.CustomPage;
import org.springframework.data.domain.Page;

public interface CommentService {

    CommentVo findById(Long id);

    CustomPage findAll(int pageNo, int pageSize);

    Page findByOrigin(int pageNo, int pageSize, String origin);

    CommentVo save(Comment comment);

    void delete(Long id);
}
