package com.xdbin.comment.controller;

import com.xdbin.annotation.Security;
import com.xdbin.bean.ErrorBean;
import com.xdbin.comment.config.AuthorSign;
import com.xdbin.comment.entity.Comment;
import com.xdbin.comment.model.CommentVo;
import com.xdbin.comment.service.CommentService;
import io.jsonwebtoken.lang.Assert;
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
    private AuthorSign authorSign;

    @Resource
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity queryByPage(@RequestParam(value = "origin") String origin, @RequestParam(value = "page", required = false) Integer page) {
        if (StringUtils.isEmpty(page)) page = 1;
        return ResponseEntity.ok(commentService.findByOrigin(page, 10, origin));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity queryOne(@PathVariable Long id) {
        Assert.notNull(id, "Comment id is not null!");
        return ResponseEntity.ok(commentService.findById(id));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity queryAll(@RequestParam(value = "page", required = false) Integer page) {
        if (StringUtils.isEmpty(page)) page = 1;
        return ResponseEntity.ok(commentService.findAll(page, 20));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Comment comment) {
        Assert.notNull(comment, "Comment is not null!");
        Assert.notNull(comment.getUsername(), "Comment is not null!");
        Assert.notNull(comment.getWebsite(), "Website is not null!");
        Assert.notNull(comment.getEmail(), "Email is not null!");

        if (!authorSign.verity(comment.getEmail(), comment.getUsername(), comment.getWebsite())) {
            return ResponseEntity.ok(new ErrorBean(400, "昵称非法"));
        }
        CommentVo ret = commentService.save(comment);
        return ResponseEntity.ok(ret);
    }

    @Security
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        Assert.notNull(id, "Comment id is not null!");
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }

}
