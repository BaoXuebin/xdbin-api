package com.xdbin.web;

import com.xdbin.Bean.ErrorBean;
import com.xdbin.config.DicConstants;
import com.xdbin.domain.Tag;
import com.xdbin.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@RestController
@CrossOrigin(value = "*")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public ResponseEntity tags() {
        List<Tag> tags = new ArrayList<>();
        Map<Long, String> tagMap = DicConstants.getInstance().getTagMap();
        tagMap.forEach((k, v) -> {
            tags.add(new Tag(k, v));
        });
        return ResponseEntity.ok(tags);
    }

    @RequestMapping(value = "/tag/add", method = RequestMethod.POST)
    public ResponseEntity addTag(String name) {
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.ok(new ErrorBean(400, "标签不能为空"));
        }
        Tag tag = new Tag();
        tag.setTagName(name.trim());
        tagService.saveTag(tag);
        return ResponseEntity.ok(tag);
    }

    @RequestMapping(value = "/tag/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteTag(long id) {
        return null;
    }

}
