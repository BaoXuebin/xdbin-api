package com.xdbin.lab.controller;

import com.xdbin.annotation.Security;
import com.xdbin.common.constants.Constants;
import com.xdbin.common.service.RestService;
import com.xdbin.lab.condition.BookCommentCondition;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.entity.book.BookComment;
import com.xdbin.lab.service.BookService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @Resource
    private RestService restService;

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public ResponseEntity findPubBookByBookId(@PathVariable(value = "bookId") Integer bookId) {
        Assert.notNull(bookId);
        return ResponseEntity.ok(bookService.findBookById(bookId, Constants.PUB));
    }

    @RequestMapping(value = "/all/{bookId}", method = RequestMethod.GET)
    public ResponseEntity findBookByBookId(@PathVariable(value = "bookId") Integer bookId) {
        Assert.notNull(bookId);
        return ResponseEntity.ok(bookService.findBookById(bookId, null));
    }

    @Security
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity input(@RequestBody BookCondition bookCondition) {
        Assert.notNull(bookCondition);
        return ResponseEntity.ok(bookService.saveOrUpdateBook(bookCondition));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity pubBooks(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "publisher", required = false) String publisher,
                                   @RequestParam(value = "progress", required = false) Integer progress) {
        BookCondition condition = new BookCondition();
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setTitle(title);
        condition.setPublisher(publisher);
        condition.setProgress(progress);
        return ResponseEntity.ok(bookService.queryPubBooks(condition));
    }

    @Security
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity allBooks(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "publisher", required = false) String publisher,
                                   @RequestParam(value = "progress", required = false) Integer progress,
                                   @RequestParam(value = "pub", required = false) Integer pub) {
        BookCondition condition = new BookCondition();
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setTitle(title);
        condition.setPublisher(publisher);
        condition.setProgress(progress);
        condition.setPub(pub);
        return ResponseEntity.ok(bookService.queryAllBooks(condition));
    }

    @Security
    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable(value = "bookId") Integer bookId) {
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }

    @Security
    @RequestMapping(value = "/{bookId}/progress", method = RequestMethod.POST)
    public ResponseEntity updateProgress(@PathVariable(value = "bookId") Integer bookId,
                                         @RequestParam(value = "progress") Integer progress) {
        return ResponseEntity.ok(bookService.updateBookProgress(bookId, progress));
    }

    @Security
    @RequestMapping(value = "/isbn/{isbn}", method = RequestMethod.GET)
    public ResponseEntity fetchDouban(@PathVariable(value = "isbn") String ispn) {
        return restService.fetch("https://api.douban.com/v2/book/isbn/" + ispn + "?apikey=0df993c66c0c636e29ecbb5344252a4a", HttpMethod.GET, null);
    }

    @RequestMapping(value = "/{bookId}/comment", method = RequestMethod.GET)
    public ResponseEntity findPubComments(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @PathVariable(value = "bookId") Integer bookId,
                                          @RequestParam(value = "chapter", required = false) Integer chapter,
                                          @RequestParam(value = "section", required = false) Integer section,
                                          @RequestParam(value = "beginDate", required = false) Date beginDate,
                                          @RequestParam(value = "endDate", required = false) Date endDate) {
        BookCommentCondition condition = new BookCommentCondition();
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setBookId(bookId);
        condition.setSection(section);
        condition.setChapter(chapter);
        condition.setBeginDate(beginDate);
        condition.setEndDate(endDate);
        return ResponseEntity.ok(bookService.queryPubBookComments(condition));
    }

    @Security
    @RequestMapping(value = "/{bookId}/comment/all", method = RequestMethod.GET)
    public ResponseEntity findAllComments(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @PathVariable(value = "bookId", required = false) Integer bookId,
                                          @RequestParam(value = "chapter", required = false) Integer chapter,
                                          @RequestParam(value = "section", required = false) Integer section,
                                          @RequestParam(value = "beginDate", required = false) Date beginDate,
                                          @RequestParam(value = "endDate", required = false) Date endDate,
                                          @RequestParam(value = "pub", required = false) Integer pub) {
        BookCommentCondition condition = new BookCommentCondition();
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setBookId(bookId);
        condition.setSection(section);
        condition.setChapter(chapter);
        condition.setBeginDate(beginDate);
        condition.setEndDate(endDate);
        condition.setPub(pub);
        return ResponseEntity.ok(bookService.queryAllBookComments(condition));
    }

    @Security
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity saveOrUpdateComment(@RequestBody BookComment comment) {
        Assert.notNull(comment);
        return ResponseEntity.ok(bookService.saveOrUpdateBookComment(comment));
    }

    @Security
    @RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBookComment(@PathVariable(value = "commentId") Integer commentId) {
        return ResponseEntity.ok(bookService.deleteBookComment(commentId));
    }

}
