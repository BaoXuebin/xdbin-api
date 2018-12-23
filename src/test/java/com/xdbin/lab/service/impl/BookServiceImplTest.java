package com.xdbin.lab.service.impl;

import com.xdbin.ApiApplication;
import com.xdbin.common.base.CustomPage;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import(ApiApplication.class)
public class BookServiceImplTest {

    @Resource
    private BookService bookService;

    @Test
    public void queryPubBooks() {
        BookCondition condition = new BookCondition();
        CustomPage page = bookService.queryPubBooks(condition);
        System.out.println(page);
    }

    @Test
    public void queryAllBooks() {
    }
}