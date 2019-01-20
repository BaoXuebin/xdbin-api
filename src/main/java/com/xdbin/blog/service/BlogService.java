package com.xdbin.blog.service;

import com.xdbin.blog.condition.BlogCondition;
import com.xdbin.blog.entity.Blog;
import com.xdbin.blog.model.*;
import com.xdbin.common.base.CustomPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {

    BlogDetailBean getPublicBlogDetailById(String blogId);

    UpdateBlogBean getUpdateBlogById(String blogId);

    String saveBlog(BlogBean blogBean);


    Blog saveBlog(Blog blog);

    void deleteBlog(String blogId);

    List<BlogItemBean> getPublicBlogsByPage(int page);

    Page<Blog> getAllBlogsByPage(int page);

    void setBlogPublic(String blogId);

    void setBlogPrivate(String blogId);

    /**
     * 条件查询博客列表
     * @param condition 条件集合
     * @return List<BlogItemBean>
     */
    List<BlogItemBean> getBlogsByCondition(BlogCondition condition);

    CustomPage getBlogListByCondition(BlogCondition blogCondition);

    /**
     * 按月份对 blog 分组
     * @return List<Monthly>
     */
    List groupByMonth();

}
