package com.xdbin.web;

import com.xdbin.bean.ErrorBean;
import com.xdbin.domain.Comment;
import com.xdbin.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: BaoXuebin
 * Date: 2018/9/13
 * Time: 下午11:26
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity queryByPage(@RequestParam(value = "origin") String origin, @RequestParam(value = "page", required = false) Integer page) {
        if (StringUtils.isEmpty(page)) page = 1;
        return ResponseEntity.ok(commentService.getCommentsByOrigin(page, 10, origin));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity queryOne(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Comment comment) {
        if ("止于秋分".equals(comment.getUsername())) {
            if (!"baoxbin@qq.com".equals(comment.getEmail()) || !"https://xdbin.com".equals(comment.getWebsite())) {
                return ResponseEntity.ok(new ErrorBean(400, "昵称非法"));
            }
        }
        Comment ret = commentService.saveComment(comment);
        return ResponseEntity.ok(ret);
    }

}
