package com.xdbin.repository;

import com.xdbin.domain.BlogTagMapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: BaoXuebin
 * Date: 2018/10/23
 * Time: 下午11:07
 */
public interface BlogTagMapperRepository extends JpaRepository<BlogTagMapper, Long> {

    void deleteAllByBlogId(String blogId);

}
