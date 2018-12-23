package com.xdbin.lab.service;

import com.xdbin.common.base.CustomPage;
import com.xdbin.lab.condition.BookCommentCondition;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.entity.book.BookComment;
import com.xdbin.lab.entity.book.BookProgress;

public interface BookService {

    /**
     * 根据ID查询书籍
     * @param bookId bookId
     * @return
     */
    BookCondition findBookById(Integer bookId, Integer pub);

    /**
     * 录入书籍
     * @param bookCondition bookCondition
     * @return Book
     */
    BookCondition saveOrUpdateBook(BookCondition bookCondition);

    /**
     * 条件查询公开书籍
     * @param bookCondition bookCondition
     * @return CustomPage
     */
    CustomPage queryPubBooks(BookCondition bookCondition);

    /**
     * 条件查询所有书籍
     * @param bookCondition bookCondition
     * @return CustomPage
     */
    CustomPage queryAllBooks(BookCondition bookCondition);

    /**
     * 删除书籍
     * @param bookId bookId
     * @return BookCondition
     */
    BookCondition deleteBook(Integer bookId);

    /**
     * 更新读书进度
     * @param bookId bookId
     * @param progress progress
     * @return BookProgress
     */
    BookProgress updateBookProgress(Integer bookId, Integer progress);

    /**
     * 条件查询公开笔记
     * @param bookCommentCondition bookCommentCondition
     * @return CustomPage
     */
    CustomPage queryPubBookComments(BookCommentCondition bookCommentCondition);

    /**
     * 条件查询全部笔记
     * @param bookCommentCondition bookCommentCondition
     * @return CustomPage
     */
    CustomPage queryAllBookComments(BookCommentCondition bookCommentCondition);

    /**
     * 添加读书笔记
     * @param bookComment saveOrUpdateBookComment
     * @return BookComment
     */
    BookComment saveOrUpdateBookComment(BookComment bookComment);

    /**
     * 删除读书笔记
     * @param bookCommentId bookCommentId
     * @return BookComment
     */
    BookComment deleteBookComment(Integer bookCommentId);
}
