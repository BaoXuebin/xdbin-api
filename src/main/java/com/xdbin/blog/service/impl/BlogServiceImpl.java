package com.xdbin.blog.service.impl;

import com.xdbin.blog.BlogSql;
import com.xdbin.blog.condition.BlogCondition;
import com.xdbin.blog.entity.Blog;
import com.xdbin.blog.entity.BlogItem;
import com.xdbin.blog.entity.MonthlyBlog;
import com.xdbin.blog.model.*;
import com.xdbin.blog.repository.BlogRepository;
import com.xdbin.blog.service.BlogService;
import com.xdbin.blog.vo.BlogItemView;
import com.xdbin.common.base.CustomPage;
import com.xdbin.common.repository.NativeQueryRepository;
import com.xdbin.common.utils.SqlUtil;
import com.xdbin.config.PathProperty;
import com.xdbin.tag.entity.BlogTagMapper;
import com.xdbin.tag.repository.BlogTagMapperRepository;
import com.xdbin.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private PathProperty pathProperty;

    @Resource
    private BlogRepository blogRepository;

    @Resource
    private BlogTagMapperRepository blogTagMapperRepository;

    private final NativeQueryRepository nativeQueryRepository;

    @Autowired
    public BlogServiceImpl(@Qualifier("nativeQueryRepository") NativeQueryRepository nativeQueryRepository) {
        this.nativeQueryRepository = nativeQueryRepository;
    }

    @Override
    public BlogDetailBean getPublicBlogDetailById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.findPublicBlog(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return BlogDetailBean.parseBean(blog, content);
        }
        return null;
    }

    @Override
    public UpdateBlogBean getUpdateBlogById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.getOne(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return UpdateBlogBean.parseBean(blog, content);
        }
        return null;
    }

    @Override
    public String saveBlog(BlogBean blogBean) {
        /*
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
        blog.setSummaryTextType(defaultMarkdown(blogBean.getSummaryType()));
        blog.setSummary(blogBean.getSummary());
        blog.setContentTextType(defaultMarkdown(blogBean.getContentType()));
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

    private Integer defaultMarkdown(Integer type) {
        if (type == null) return Blog.MARKDOWN;
        return type;
    }


    @Override
    public Blog saveBlog(Blog blog) {
        if (blog != null)
            return blogRepository.save(blog);
        return null;
    }

    @Override
    public void deleteBlog(String blogId) {
        blogRepository.delete(blogId);
    }

    @Override
    public List<BlogItemBean> getPublicBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        return parseBeanList(blogRepository.findPubBlogsByPage(new PageRequest(page - 1, 10, s)));
    }

    @Override
    public Page<Blog> getAllBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
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

    @Override
    public void setBlogPublic(String blogId) {
        blogRepository.updateBlogPubByBlogId(1, blogId);
    }

    @Override
    public void setBlogPrivate(String blogId) {
        blogRepository.updateBlogPubByBlogId(0, blogId);
    }

    /**
     * 条件查询博客列表
     * @param condition 条件集合
     * @return List<BlogItemBean>
     */
    @Override
    public List<BlogItemBean> getBlogsByCondition(BlogCondition condition) {
        List<BlogItemBean> blogItemBeans = Collections.emptyList();
        if (StringUtils.isEmpty(condition)) return blogItemBeans;

        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Blog> blogPage = blogRepository.findAll(where(condition), new PageRequest(9, 10, s));
        if (blogPage.getContent().size() > 0) {
            blogItemBeans = blogPage.getContent().stream().map(BlogItemBean::parseBean).collect(toList());
        }
        return blogItemBeans;
    }

    @Override
    public CustomPage getBlogListByCondition(BlogCondition blogCondition) {
        Map<String, Object> params = mapBlogCondition(blogCondition);
        CustomPage page = nativeQueryRepository.nativeQueryForPage(
                BlogSql.buildQueryBlogListSQL(params),
                BlogSql.buildCountBlogListSQL(params),
                params,
                BlogItem.class
        );
        page.mapEntity((model) -> BlogItemView.from((BlogItem) model));
        return page;
    }

    @Override
    public List groupByMonth() {
        return nativeQueryRepository.nativeQueryForList(BlogSql.GROUP_BY_MONTHLY, null, MonthlyBlog.class);
    }

    /**
     * private function.
     */

    private Map<String, Object> mapBlogCondition(BlogCondition condition) {
        if (StringUtils.isEmpty(condition)) return null;
        return new HashMap<String, Object>() {{
            put("pageNo", condition.getPageNo());
            put("pageSize", condition.getPageSize());
            put("title", SqlUtil.like(condition.getTitle()));
            put("month", condition.getMonth());
            put("tagId", condition.getTagId());
            put("pub", condition.getPub());
        }};
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
            // 查询月份
            if (!StringUtils.isEmpty(blogCondition.getMonth())) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("DATE_FORMAT", String.class, root.get("updateTime"), criteriaBuilder.parameter(String.class, "formatStr")) , blogCondition.getMonth()));
            }
            // 是否公开
            if (blogCondition.getPub() != 0) {
                // 只查询公开
                predicates.add(criteriaBuilder.equal(root.get("ifPub").as(Integer.class), 1));
            }

            CriteriaQuery cq = criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

            // 查询月份
            if (!StringUtils.isEmpty(blogCondition.getMonth())) {
                Query tq = em.createQuery(cq);
                tq.setParameter("formatStr", "%Y-%m");
            }

            Predicate predicate = cq.getRestriction();
            return predicate;
        };
    }

}
