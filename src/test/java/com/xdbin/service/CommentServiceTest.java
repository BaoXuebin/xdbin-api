package com.xdbin.service;

import com.xdbin.ApiApplication;
import com.xdbin.comment.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2017/10/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class CommentServiceTest {

    @Resource
    private CommentService commentService;

    @Test
    public void test() throws Exception {
        commentService.findAll(2, 2).getContent().forEach(System.out::println);
    }

}