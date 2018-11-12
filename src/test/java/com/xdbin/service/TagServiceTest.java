package com.xdbin.service;

import com.xdbin.ApiApplication;
import com.xdbin.tag.repository.BlogTagMapperRepository;
import com.xdbin.tag.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2017/10/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class TagServiceTest {

    @Resource
    private TagService tagService;

    @Resource
    BlogTagMapperRepository blogTagMapperRepository;

    @Test
    public void isExit() throws Exception {
        // blogTagMapperRepository.nativeQueryForList("SELECT t1.tag_id, t1.tag_name, t2.count FROM dic_tag t1 LEFT JOIN (SELECT tag_id, COUNT(blog_id) AS count FROM t_blog_tag GROUP BY tag_id) t2 ON t1.tag_id = t2.tag_id", null).stream().forEach(System.out::println);
    }

}