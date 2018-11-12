package com.xdbin.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author: BaoXuebin
 * Date: 2018/10/23
 * Time: 下午11:05
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_BLOG_TAG")
public class BlogTagMapper {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String blogId;

    private Long tagId;

    public BlogTagMapper(String blogId, Long tagId) {
        this.blogId = blogId;
        this.tagId = tagId;
    }

}
