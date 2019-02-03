package com.xdbin.lab.service;

import com.xdbin.condition.VideoCondition;
import com.xdbin.domain.Video;
import com.xdbin.lab.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午3:33
 */
public interface VideoService {

    /**
     * 分页查询所有 video
     * @param page 页数
     * @return CustomPage
     */
    Page<Video> getAllVideoByPage(int page, int pageSize);

    /**
     * 分页查询公开的 video
     * @param videoCondition condition
     * @return CustomPage
     */
    Page<Video> getPubVideoByCondition(VideoCondition videoCondition);

    /**
     * 保存
     * @param video v
     * @return Video
     */
    Video insert(Video video);

    /**
     * 删除
     * @param videoId videoId
     */
    void delete(Integer videoId);

}
