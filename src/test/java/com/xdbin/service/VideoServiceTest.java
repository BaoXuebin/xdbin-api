package com.xdbin.service;

import com.xdbin.ApiApplication;
import com.xdbin.lab.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午3:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class VideoServiceTest {

    @Resource
    private VideoService videoService;

    @Test
    public void getAllVideoByPage() throws Exception {
        System.out.println(videoService.getAllVideoByPage(1, 10));
    }

    @Test
    public void getPubVideoByCondition() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

}