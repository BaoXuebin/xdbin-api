package com.xdbin.lab.condition;

import com.xdbin.condition.PageCondition;
import com.xdbin.lab.entity.book.Book;
import com.xdbin.lab.entity.book.BookProgress;
import com.xdbin.utils.ConvertUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookCondition extends PageCondition implements Serializable {

    private Integer id;
    private String isbn;
    private String title;
    private String subTitle;
    private String publisher; // 出版社
    private String publishDate;
    private String price;
    private String pages; // 页数
    private String summary;
    private String image;
    private String doubanLink;
    private Integer pub;

    private Integer progress;
    private Date progressUpdateTime;

    private List<String> authors;
    private List<String> translators;
    private List<String> tagGroup;

    public static BookCondition from(Book book) {
        if (StringUtils.isEmpty(book)) return null;

        BookCondition condition = new BookCondition();
        condition.setId(book.getId());
        condition.setIsbn(book.getIsbn());
        condition.setTitle(book.getTitle());
        condition.setSubTitle(book.getSubTitle());
        condition.setPublisher(book.getPublisher());
        condition.setPublishDate(book.getPublishDate());
        condition.setPrice(book.getPrice());
        condition.setPages(book.getPages());
        condition.setSummary(book.getSummary());
        condition.setImage(book.getImage());
        condition.setDoubanLink(book.getDoubanLink());
        condition.setPub(book.getPub());

        condition.setAuthors(ConvertUtil.split(book.getAuthor(), "\\|"));
        condition.setTranslators(ConvertUtil.split(book.getTranslator(), "\\|"));
        condition.setTagGroup(ConvertUtil.split(book.getTags(), "\\|"));

        return condition;
    }

    public static BookCondition from(Book book, BookProgress bookProgress) {
        BookCondition condition = from(book);
        if (StringUtils.isEmpty(condition)) return null;
        if (!StringUtils.isEmpty(bookProgress) &&
                !StringUtils.isEmpty(bookProgress.getId()) &&
                bookProgress.getBookId().equals(book.getId())) {
            condition.setProgress(bookProgress.getProgress());
            condition.setProgressUpdateTime(bookProgress.getUpdateTime());
        }

        return condition;
    }

}
