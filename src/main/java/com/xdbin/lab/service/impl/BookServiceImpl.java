package com.xdbin.lab.service.impl;

import com.xdbin.common.constants.Constants;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.entity.book.Book;
import com.xdbin.lab.entity.book.BookProgress;
import com.xdbin.lab.repository.book.BookProgressRepository;
import com.xdbin.lab.repository.book.BookRepository;
import com.xdbin.lab.service.BookService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookProgressRepository bookProgressRepository;

    @Override
    @Transactional
    public Book inputBook(BookCondition bookCondition) {
        Assert.notNull(bookCondition);
        Book book = bookRepository.save(Book.from(bookCondition));
        if (!StringUtils.isEmpty(book)) {
            BookProgress bookProgress = new BookProgress();
            bookProgress.setBookId(book.getId());
            bookProgress.setUpdateTime(new Date());
            bookProgress.setProgress(0);
            bookProgress.setValid(Constants.DELETE_FLAG_NORMAL);
            bookProgressRepository.save(bookProgress);
        }
        return book;
    }
}
