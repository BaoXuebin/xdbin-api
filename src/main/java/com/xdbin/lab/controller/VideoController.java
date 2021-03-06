package com.xdbin.lab.controller;

import com.xdbin.annotation.Security;
import com.xdbin.bean.ErrorBean;
import com.xdbin.condition.VideoCondition;
import com.xdbin.domain.Video;
import com.xdbin.lab.service.VideoService;
import com.xdbin.utils.ConvertUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午4:17
 */
@RestController
@RequestMapping(value = "/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @Security
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity all(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        return ResponseEntity.ok(videoService.getAllVideoByPage(page, pageSize));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity pub(String page, String type) {
        int _page = ConvertUtil.parseInteger(page, 1);

        VideoCondition condition = new VideoCondition();
        condition.setPageNo(_page);
        condition.setType(type);

        return ResponseEntity.ok(videoService.getPubVideoByCondition(condition));
    }

    @Security
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Video video) {
        if (video != null) {
            if (StringUtils.isEmpty(video.getName())) {
                return ResponseEntity.ok(new ErrorBean(400, "短片名称不能为空"));
            } else if (StringUtils.isEmpty(video.getImage())) {
                return ResponseEntity.ok(new ErrorBean(400, "短片封面不能为空"));
            } else if (StringUtils.isEmpty(video.getSource())) {
                return ResponseEntity.ok(new ErrorBean(400, "短片资源不能为空"));
            } else {
                video = videoService.insert(video);
                return ResponseEntity.ok(video);
            }
        }
        return ResponseEntity.ok(new ErrorBean(400, "短片不能为空"));
    }

    @Security
    @RequestMapping(value = "/{videoId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("videoId") String id) {
        int _id = ConvertUtil.parseInteger(id, -1);
        if (_id > 0) {
            videoService.delete(_id);
        }
        return ResponseEntity.ok(id);
    }

}
