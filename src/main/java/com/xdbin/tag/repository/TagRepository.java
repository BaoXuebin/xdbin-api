package com.xdbin.tag.repository;

import com.xdbin.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTagName(String tagName);

}
