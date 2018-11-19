package com.xdbin.service;

import com.xdbin.bean.*;
import com.xdbin.config.PathProperty;
import com.xdbin.domain.Blog;
import com.xdbin.tag.entity.BlogTagMapper;
import com.xdbin.tag.repository.BlogTagMapperRepository;
import com.xdbin.utils.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@Service
@Transactional
public class BlogService {

    @Resource
    private PathProperty pathProperty;

    @Resource
    private BlogRepository blogRepository;

    @Resource
    private BlogTagMapperRepository blogTagMapperRepository;

    public BlogDetailBean getPublicBlogDetailById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.findPublicBlog(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return BlogDetailBean.parseBean(blog, content);
        }
        return null;
    }

    public UpdateBlogBean getUpdateBlogById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.getOne(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return UpdateBlogBean.parseBean(blog, content);
        }
        return null;
    }

    public String saveBlog(BlogBean blogBean) {
        /**
         * 查询是否有原来的实体
         */
        Blog oldBlog = null;
        if (!StringUtils.isEmpty(blogBean.getBlogId())) {
            oldBlog = blogRepository.getOne(blogBean.getBlogId());
        }

        // base + time + name.md
        String suffix = "";
        String originUrl = null;
        if (!StringUtils.isEmpty(oldBlog)) {
            suffix = "-ver" + (oldBlog.getEditNum() + 1);
            originUrl = oldBlog.getContentUrl();
        }
        String contentUrl = FileUtil.writeBlogContent(pathProperty.getBlog(), originUrl, blogBean.getContent(), suffix);
        if (StringUtils.isEmpty(contentUrl)) {
            return null;
        }

        // 标签后加 ,
        String tags = "," + String.join(",", blogBean.getTags().stream().map(tag -> String.valueOf(tag.getTagId())).collect(toList())) + ",";

        Blog blog = new Blog();
        blog.setBlogId(blogBean.getBlogId());
        // 变更
        if (!StringUtils.isEmpty(oldBlog)) {
            blog.setEditNum(oldBlog.getEditNum() + 1);
            blog.setPublishTime(oldBlog.getPublishTime());
        } else {
            blog.setEditNum(0);
            blog.setPublishTime(new Date());
        }
        blog.setUpdateTime(new Date());
        blog.setTitle(blogBean.getTitle());
        blog.setTags(tags);
        blog.setSummaryTextType(Blog.MARKDOWN);
        blog.setSummary(blogBean.getSummary());
        blog.setContentTextType(Blog.MARKDOWN);
        blog.setContentUrl(contentUrl);
        blog.setIfPub(blogBean.isIfPub() ? 1 : 0);

        Blog b = saveBlog(blog);

        if (b == null) return null;
        String blogId = b.getBlogId();
        // 删除原有的标签
        blogTagMapperRepository.deleteAllByBlogId(blogId);
        blogBean.getTags().forEach((tag) -> {
            blogTagMapperRepository.save(new BlogTagMapper(blogId, tag.getTagId()));
        });

        return b.getBlogId();
    }


    public Blog saveBlog(Blog blog) {
        if (blog != null)
            return blogRepository.save(blog);
        return null;
    }

    public void deleteBlog(String blogId) {
        blogRepository.delete(blogId);
    }

    public List<BlogItemBean> getPublicBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        return parseBeanList(blogRepository.findPubBlogsByPage(new PageRequest(page - 1, 10, s)));
    }

    private List<BlogItemBean> parseBeanList(List<Blog> blogList) {
        List<BlogItemBean> blogItemBeans = Collections.emptyList();
        if (!StringUtils.isEmpty(blogList) && blogList.size() > 0) {
            blogItemBeans = blogList.stream()
                    .map(BlogItemBean::parseBean)
                    .collect(toList());
        }
        return blogItemBeans;
    }

    public Page<Blog> getAllBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        return blogRepository.findAll(new PageRequest(page - 1, 10, s));
    }

    private List<BlogTableBean> parseBlogTableList(List<Blog> blogList) {
        List<BlogTableBean> blogTableBeans = Collections.emptyList();
        if (!StringUtils.isEmpty(blogList) && blogList.size() > 0) {
            blogTableBeans = blogList.stream()
                    .map(BlogTableBean::parseBean)
                    .collect(toList());
        }
        return blogTableBeans;
    }

    public void setBlogPublic(String blogId) {
        blogRepository.updateBlogPubByBlogId(1, blogId);
    }

    public void setBlogPrivate(String blogId) {
        blogRepository.updateBlogPubByBlogId(0, blogId);
    }

    /**
     * 条件查询博客列表
     * @param condition 条件集合
     * @return List<BlogItemBean>
     */
    public List<BlogItemBean> getBlogsByCondition(BlogCondition condition) {
        List<BlogItemBean> blogItemBeans = Collections.emptyList();
        if (StringUtils.isEmpty(condition)) return blogItemBeans;

        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        Page<Blog> blogPage = blogRepository.findAll(where(condition), new PageRequest(condition.getPage() - 1, 10, s));
        if (blogPage.getContent().size() > 0) {
            blogItemBeans = blogPage.getContent().stream().map(BlogItemBean::parseBean).collect(toList());
        }
        return blogItemBeans;
    }

    private Specification<Blog> where(BlogCondition blogCondition) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 模糊匹配标题
            if (!StringUtils.isEmpty(blogCondition.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + blogCondition.getTitle() + "%"));
            }
            // 模糊查询标签
            if (!StringUtils.isEmpty(blogCondition.getTagId())) {
                predicates.add(criteriaBuilder.like(root.get("tags").as(String.class), "%," + blogCondition.getTagId() + ",%"));
            }
            // 是否公开
            if (blogCondition.getPub() != 0) {
                // 只查询公开
                predicates.add(criteriaBuilder.equal(root.get("ifPub").as(Integer.class), 1));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

}
