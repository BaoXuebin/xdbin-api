package com.xdbin.service;

import com.xdbin.vo.CommentVo;
import com.xdbin.domain.Comment;
import com.xdbin.repository.CommentRepository;
import com.xdbin.vo.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: BaoXuebin
 * Date: 2018/9/13
 * Time: 下午11:15
 */
@Service
public class CommentService {

    @Resource
    private CommentRepository commentRepository;

    /**
     * 指定 id 查询评论
     */
    public CommentVo getCommentById(Long id) {
         Comment comment = commentRepository.getCommentById(id);
        return new CommentVo(comment);
    }

    /**
     * 查询指定 origin 下的所有评论
     */
    public PageVo<CommentVo> getCommentsByOrigin(int pageNo, int pageSize, String origin) {
        Sort s = new Sort(Sort.Direction.ASC, "publishTime");
        return desensitize(commentRepository.getCommentsByOrigin(origin, new PageRequest(pageNo - 1, pageSize, s)));
    }

    /**
     * 添加评论
     * @param comment comment
     * @return Comment
     */
    @Transactional
    public Comment saveComment(Comment comment) {
        comment.setPublishTime(new Date());
        comment.setValid(1); // 手动设置为有效值
        return commentRepository.save(comment);
    }

    private PageVo<CommentVo> desensitize(Page<Comment> page) {
        List<Comment> content = page.getContent();
        List<CommentVo> commentVos = content.stream().map(CommentVo::parse).collect(Collectors.toList());
        return new PageVo<>(page.getNumber(), page.getSize(), page.getTotalElements(), commentVos);
    }

}
