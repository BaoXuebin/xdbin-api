package com.xdbin.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBlog {

    // 月份
    @Id
    private String month;

    // 笔记数量
    private Integer count;

}
