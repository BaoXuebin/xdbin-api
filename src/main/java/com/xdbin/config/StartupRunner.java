package com.xdbin.config;

import com.xdbin.tag.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(StartupRunner.class);

    @Resource
    private TagService tagService;

    @Override
    public void run(String... strings) throws Exception {
        tagService.refreshTagMap();
        logger.info("Tag map init success.");
    }
}