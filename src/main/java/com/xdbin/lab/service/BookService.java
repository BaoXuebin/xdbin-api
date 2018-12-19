package com.xdbin.lab.service;

import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.entity.book.Book;

public interface BookService {

    /**
     * 录入书籍
     * @param bookCondition bookCondition
     * @return Book
     */
    Book inputBook(BookCondition bookCondition);

}
