package com.xdbin.lab.service.impl;

import com.xdbin.common.base.CustomPage;
import com.xdbin.common.constants.Constants;
import com.xdbin.common.repository.NativeQueryRepository;
import com.xdbin.lab.condition.BookCommentCondition;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.lab.entity.book.Book;
import com.xdbin.lab.entity.book.BookComment;
import com.xdbin.lab.entity.book.BookProgress;
import com.xdbin.lab.entity.book.BookModel;
import com.xdbin.lab.repository.book.BookCommentRepository;
import com.xdbin.lab.repository.book.BookProgressRepository;
import com.xdbin.lab.repository.book.BookRepository;
import com.xdbin.lab.service.BookService;
import com.xdbin.lab.sql.BookSql;
import com.xdbin.lab.vo.BookVO;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookProgressRepository bookProgressRepository;

    @Resource
    private BookCommentRepository bookCommentRepository;

    @Resource
    private NativeQueryRepository nativeQueryRepository;

    private Map<String, Object> mapCondition(BookCondition condition) {
        if (StringUtils.isEmpty(condition)) return null;
        return new HashMap<String, Object>() {{
            put("pageNo", condition.getPageNo());
            put("pageSize", condition.getPageSize());
            put("title", StringUtils.isEmpty(condition.getTitle()) ? null : "%" + condition.getTitle() + "%");
            put("publisher", StringUtils.isEmpty(condition.getPublisher()) ? null : "%" + condition.getPublisher() + "%");
            put("progress", condition.getProgress());
            put("pub", condition.getPub());
        }};
    }

    private Map<String, Object> mapCondition(BookCommentCondition condition) {
        if (StringUtils.isEmpty(condition)) return null;
        return new HashMap<String, Object>() {{
            put("pageNo", condition.getPageNo());
            put("pageSize", condition.getPageSize());
            put("bookId", condition.getBookId());
            put("chapter", condition.getChapter());
            put("section", condition.getSection());
            put("beginDate", condition.getBeginDate());
            put("endDate", condition.getEndDate());
            put("pub", condition.getPub());
        }};
    }

    @Override
    public BookCondition findBookById(Integer bookId, Integer pub) {
        Book book = null;
        if (pub != null && pub.equals(Constants.PUB)) {
            book = bookRepository.findByIdAndPub(bookId, Constants.PUB);
        } else {
            book = bookRepository.findOne(bookId);
        }
        BookProgress progress = bookProgressRepository.findByBookId(bookId);
        return BookCondition.from(book, progress);
    }

    @Override
    public BookCondition saveOrUpdateBook(BookCondition bookCondition) {
        Assert.notNull(bookCondition);
        Book book = bookRepository.save(Book.from(bookCondition));

        BookProgress bookProgress = null;
        if (!StringUtils.isEmpty(book)) {
            bookProgress = new BookProgress();
            bookProgress.setBookId(book.getId());
            bookProgress.setUpdateTime(new Date());
            bookProgress.setProgress(0);
            bookProgress.setValid(Constants.DELETE_FLAG_NORMAL);
            bookProgress = bookProgressRepository.save(bookProgress);
        }
        return BookCondition.from(book, bookProgress);
    }

    @Override
    public CustomPage queryPubBooks(BookCondition bookCondition) {
        Map<String, Object> params = mapCondition(bookCondition);
        if (!StringUtils.isEmpty(params)) {
            params.put("pub", Constants.PUB);
        }
        return nativeQueryRepository.nativeQueryForPage(
                BookSql.buildQueryBooksSQL(params),
                BookSql.buildCountBooksSQL(params),
                params,
                BookModel.class);
    }

    @Override
    public CustomPage queryAllBooks(BookCondition bookCondition) {
        Map<String, Object> params = mapCondition(bookCondition);
        CustomPage page = nativeQueryRepository.nativeQueryForPage(
                BookSql.buildQueryBooksSQL(params),
                BookSql.buildCountBooksSQL(params),
                params,
                BookModel.class);
        page.mapEntity((model) -> BookVO.from((BookModel) model));
        return page;
    }

    @Override
    @Transactional
    public BookCondition deleteBook(Integer bookId) {
        Book book = bookRepository.findOne(bookId);
        bookRepository.delete(bookId);
        return BookCondition.from(book);
    }

    @Override
    @Transactional
    public BookProgress updateBookProgress(Integer bookId, Integer progress) {
        BookProgress bookProgress = bookProgressRepository.findByBookId(bookId);
        if (StringUtils.isEmpty(bookProgress)) return null;
        bookProgress.setProgress(progress);
        bookProgress.setUpdateTime(new Date());
        return bookProgressRepository.save(bookProgress);
    }

    @Override
    public CustomPage queryPubBookComments(BookCommentCondition bookCommentCondition) {
        Map<String, Object> params = mapCondition(bookCommentCondition);
        if (!StringUtils.isEmpty(params)) {
            params.put("pub", Constants.PUB);
        }
        return nativeQueryRepository.nativeQueryForPage(
                BookSql.buildQueryBookCommentsSQL(params),
                BookSql.buildCountBookCommentsSQL(params),
                params,
                BookComment.class);
    }

    @Override
    public CustomPage queryAllBookComments(BookCommentCondition bookCommentCondition) {
        Map<String, Object> params = mapCondition(bookCommentCondition);
        return nativeQueryRepository.nativeQueryForPage(
                BookSql.buildQueryBookCommentsSQL(params),
                BookSql.buildCountBookCommentsSQL(params),
                params,
                BookComment.class);
    }

    @Override
    @Transactional
    public BookComment saveOrUpdateBookComment(BookComment bookComment) {
        if (StringUtils.isEmpty(bookComment)) return null;
        bookComment.setPublishTime(new Date());
        bookComment.setValid(Constants.DELETE_FLAG_NORMAL);
        return bookCommentRepository.save(bookComment);
    }

    @Override
    @Transactional
    public BookComment deleteBookComment(Integer bookCommentId) {
        BookComment comment = bookCommentRepository.findOne(bookCommentId);
        bookCommentRepository.delete(bookCommentId);
        return comment;
    }
}
