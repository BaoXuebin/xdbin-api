package com.xdbin.lab.controller;

import com.xdbin.annotation.Security;
import com.xdbin.common.service.RestService;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.service.BookService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @Resource
    private RestService restService;

    @Security
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity input(@RequestBody BookCondition bookCondition) {
        Assert.notNull(bookCondition);
        return ResponseEntity.ok(bookService.inputBook(bookCondition));
    }

    @Security
    @RequestMapping(value = "/isbn/{isbn}", method = RequestMethod.GET)
    public ResponseEntity fetchDouban(@PathVariable(value = "isbn") String ispn) {
        return restService.fetch("https://api.douban.com/v2/book/isbn/" + ispn, HttpMethod.GET, null);
    }


}
