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
 * Date: 2017/10/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class TagServiceTest {

    @Resource
    private TagService tagService;

    @Test
    public void isExit() throws Exception {
        System.out.println(tagService.isExit("Atom"));
        System.out.println(tagService.isExit("aaasss"));
    }

}