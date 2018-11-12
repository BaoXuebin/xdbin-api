package com.xdbin.repository;

import com.xdbin.domain.BlogTagMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: BaoXuebin
 * Date: 2018/10/23
 * Time: 下午11:07
 */
public interface BlogTagMapperRepository extends JpaRepository<BlogTagMapper, Long> {

    void deleteAllByBlogId(String blogId);

    @Query(value = "SELECT t1.tag_id, t1.tag_name, t2.count " +
            " FROM dic_tag t1 LEFT JOIN (SELECT tag_id, COUNT(blog_id) AS count FROM t_blog_tag GROUP BY tag_id) t2 ON t1.tag_id = t2.tag_id",
            nativeQuery = true)
    List<Object[]> groupByTag();
}
