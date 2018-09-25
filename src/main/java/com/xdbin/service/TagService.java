package com.xdbin.service;

import com.xdbin.config.DicConstants;
import com.xdbin.domain.Tag;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Service
@Transactional
public class TagService {

    @Resource
    private TagRepository tagRepository;

    public void saveTag(Tag tag) {
        if (!StringUtils.isEmpty(tag)) {
            tagRepository.save(tag);
            // 刷新标签
            refreshTagMap();
        }
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public boolean isExit(String tagName) {
        return tagRepository.findByTagName(tagName) != null;
    }

    public Tag deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).get();
        if (StringUtils.isEmpty(tag)) {
            return null;
        } else {
            tagRepository.deleteById(id);
            // 刷新标签
            refreshTagMap();
            return tag;
        }
    }

    public void refreshTagMap() {
        List<Tag> tags = findAllTags();
        Map<Long, String> tagMap = new HashMap<>();
        if (!StringUtils.isEmpty(tags)) {
            tags.forEach(tag -> tagMap.put(tag.getTagId(), tag.getTagName()));
        }
        DicConstants.getInstance().setTagMap(tagMap);
    }

    public String getTagIdByName(String tagName) {
        Map<Long, String> tagMap = DicConstants.getInstance().getTagMap();
        for (Long id : tagMap.keySet()) {
            if (tagMap.get(id).equals(tagName)) {
                return id.toString();
            }
        }
        return null;
    }
}
