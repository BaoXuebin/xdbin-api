package com.xdbin.service;

import com.xdbin.ApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Author: baoxuebin
 * Date: 2017/10/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class BlogServiceTest {

    @Resource
    private BlogService blogService;

    @Test
    public void setBlogPublic() throws Exception {
        blogService.setBlogPublic("ff8080815ee76e80015ee7c1af080000");
    }

    @Test
    public void setBlogPrivate() throws Exception {
        blogService.setBlogPrivate("ff8080815ee76e80015ee7c1af080000");
    }

}