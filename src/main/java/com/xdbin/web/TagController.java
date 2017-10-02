package com.xdbin.web;

import com.xdbin.Bean.ErrorBean;
import com.xdbin.annotation.Security;
import com.xdbin.config.DicConstants;
import com.xdbin.domain.Tag;
import com.xdbin.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@RestController
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

    @Security
    @RequestMapping(value = "/tag", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity addTag(@RequestBody Tag tag) {
        if (StringUtils.isEmpty(tag) || StringUtils.isEmpty(tag.getTagName())) {
            return ResponseEntity.ok(new ErrorBean(400, "标签不能为空"));
        }
        if (tagService.isExit(tag.getTagName())) {
            return ResponseEntity.ok(new ErrorBean(400, "标签已经存在"));
        }
        tagService.saveTag(tag);
        return ResponseEntity.ok(tag);
    }

    @Security
    @RequestMapping(value = "/tag", method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestBody Tag tag) {
        if (StringUtils.isEmpty(tag)) {
            return ResponseEntity.ok(new ErrorBean(400, "标签不能为空"));
        }
        tag = tagService.deleteTag(tag.getTagId());
        if (StringUtils.isEmpty(tag)) {
            return ResponseEntity.ok(new ErrorBean(400, "标签不存在"));
        }
        return ResponseEntity.ok(tag);
    }

}
