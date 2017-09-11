package com.xdbin.service;

import com.xdbin.ApiApplication;
import com.xdbin.domain.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class BlogServiceTest {

    @Resource
    private BlogService blogService;

    @Test
    public void getPublicBlogDetailById() throws Exception {
        System.out.println(blogService.getPublicBlogDetailById("adsad"));
        System.out.println(blogService.getPublicBlogDetailById("402880845e6c5cf8015e6c5d06350000"));
    }

    @Test
    public void saveBlog() throws Exception {
        Blog blog = new Blog();
        blog.setTitle("测试博客1");
        blog.setPublishTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setSummaryTextType(1);
        blog.setSummary("**这是概要内容**  ```print('Hello World!')```");
        blog.setContentTextType(1);
        blog.setContentUrl("/ss/s");
        blog.setIfPub(1);
        blog.setTags("1,2,3");

        blogService.saveBlog(blog);
    }


}