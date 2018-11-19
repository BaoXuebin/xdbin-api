package com.xdbin.comment.service.impl;

import com.xdbin.comment.CommentSql;
import com.xdbin.comment.entity.Comment;
import com.xdbin.comment.model.CommentDetailVo;
import com.xdbin.comment.model.CommentVo;
import com.xdbin.comment.repository.CommentRepository;
import com.xdbin.comment.service.CommentService;
import com.xdbin.common.base.CustomPage;
import com.xdbin.common.constants.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    public CommentVo findById(Long id) {
        Assert.notNull(id, "Comment id is not null!");
        return CommentVo.from(commentRepository.findById(id));
    }

    @Override
    public CustomPage findAll(int pageNo, int pageSize) {
        return commentRepository.nativeQueryForPage(
                CommentSql.QUERY_COMMENT_DETAIL,
                CommentSql.COUNT_COMMENT_DETAIL,
                new HashMap<String, Object>() {{
                    put("pageNo", pageNo);
                    put("pageSize", pageSize);
                }},
                CommentDetailVo.class);
    }

    @Override
    public Page<CommentVo> findByOrigin(int pageNo, int pageSize, String origin) {
        Sort s = new Sort(Sort.Direction.ASC, "publishTime");
        Page<Comment> commentPage = commentRepository.findAllByOrigin(origin, new PageRequest(pageNo - 1, pageSize, s));
        return commentPage.map(CommentVo::from);
    }

    @Override
    public CommentVo save(Comment comment) {
        Assert.notNull(comment, "Comment save failed, Comment is not null!");
        comment.setPublishTime(new Date());
        comment.setValid(Constants.DELETE_FLAG_NORMAL);
        return CommentVo.from(commentRepository.save(comment));
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "Comment id is not null!");
        commentRepository.delete(id);
    }
}
