package com.xdbin.tag.repository;

import com.xdbin.common.repository.NativeQueryRepository;
import com.xdbin.tag.entity.BlogTagMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagMapperRepository extends JpaRepository<BlogTagMapper, Long>, NativeQueryRepository {

    void deleteAllByBlogId(String blogId);

}
