package com.xdbin.lab.entity.book;

import com.xdbin.common.constants.Constants;
import com.xdbin.lab.condition.BookCondition;
import com.xdbin.utils.ConvertUtil;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BOOK")
@SQLDelete(sql = "UPDATE BOOK SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE BOOK SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String translator;
    private String tags;
    private String publisher; // 出版社
    private String publishDate;
    private String price;
    private String pages; // 页数
    private String summary;
    private String image;
    private String doubanLink;
    private Integer pub;
    private Integer valid;

    public static Book from(BookCondition bookCondition) {
        if (StringUtils.isEmpty(bookCondition)) return null;
        Book book = new Book();
        book.setId(bookCondition.getId());
        book.setIsbn(bookCondition.getIsbn());
        book.setTitle(bookCondition.getTitle());
        book.setSubTitle(bookCondition.getSubTitle());
        book.setAuthor(ConvertUtil.flat(bookCondition.getAuthors(), "|"));
        book.setTranslator(ConvertUtil.flat(bookCondition.getTranslators(), "|"));
        book.setTags(ConvertUtil.flat(bookCondition.getTagGroup(), "|"));
        book.setPublisher(bookCondition.getPublisher());
        book.setPublishDate(bookCondition.getPublishDate());
        book.setPrice(bookCondition.getPrice());
        book.setPages(bookCondition.getPages());
        book.setSummary(bookCondition.getSummary());
        bookCondition.setImage(bookCondition.getImage());
        book.setDoubanLink(bookCondition.getDoubanLink());
        book.setImage(bookCondition.getImage());
        book.setPub(bookCondition.getPub());
        book.setValid(Constants.DELETE_FLAG_NORMAL);
        return book;
    }
}
