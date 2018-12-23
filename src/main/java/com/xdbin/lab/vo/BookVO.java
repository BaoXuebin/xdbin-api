package com.xdbin.lab.vo;

import com.xdbin.lab.entity.book.BookModel;
import com.xdbin.utils.ConvertUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BookVO implements Serializable {

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

    private List authors;
    private List translators;
    private List tagGroup;

    public static BookVO from(BookModel bookModel) {
        if (StringUtils.isEmpty(bookModel)) return null;
        BookVO vo = new BookVO();
        vo.setId(bookModel.getId());
        vo.setIsbn(bookModel.getIsbn());
        vo.setTitle(bookModel.getTitle());
        vo.setSubTitle(bookModel.getSubTitle());
        vo.setPublisher(bookModel.getPublisher());
        vo.setPublishDate(bookModel.getPublishDate());
        vo.setPrice(bookModel.getPrice());
        vo.setPages(bookModel.getPages());
        vo.setSummary(bookModel.getSummary());
        vo.setImage(bookModel.getImage());
        vo.setDoubanLink(bookModel.getDoubanLink());
        vo.setPub(bookModel.getPub());
        vo.setProgress(bookModel.getProgress());
        vo.setProgressUpdateTime(bookModel.getProgressUpdateTime());
        vo.setAuthors(ConvertUtil.split(bookModel.getAuthor(), "\\|"));
        vo.setTranslators(ConvertUtil.split(bookModel.getTranslator(), "\\|"));
        vo.setTagGroup(ConvertUtil.split(bookModel.getTags(), "\\|"));
        return vo;
    }

}
