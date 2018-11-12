package com.xdbin.tag.repository;

import com.xdbin.common.repository.NativeRepository;
import com.xdbin.domain.BlogTagMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagMapperRepository extends JpaRepository<BlogTagMapper, Long>, NativeRepository {

    void deleteAllByBlogId(String blogId);

}
