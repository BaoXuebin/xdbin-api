package com.xdbin.service;

import com.xdbin.condition.VideoCondition;
import com.xdbin.domain.Video;
import com.xdbin.repository.VideoRepository;
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
@Service
public class VideoService {

    @Resource
    private VideoRepository videoRepository;

    /**
     * 分页查询所有 video
     * @param page 页数
     * @return CustomPage
     */
    public Page<Video> getAllVideoByPage(int page, int pageSize) {
        Sort s = new Sort(Sort.Direction.DESC, "uploadTime");
        return videoRepository.findAll(new PageRequest(page - 1, pageSize, s));
    }

    /**
     * 分页查询公开的 video
     * @param videoCondition condition
     * @return CustomPage
     */
    public Page<Video> getPubVideoByCondition(VideoCondition videoCondition) {
        Sort s = null;
        if ("hot".equals(videoCondition.getType())) {
            s = new Sort(Sort.Direction.DESC, "heartNum");
        } else {
            s = new Sort(Sort.Direction.DESC, "uploadTime");
        }

        int page = videoCondition.getPageNo();
        return videoRepository.findVideosByPub(new PageRequest(page - 1, 9, s));
    }

    /**
     * 保存
     * @param video v
     * @return Video
     */
    public Video insert(Video video) {
        if (StringUtils.isEmpty(video.getHeartNum())) {
            video.setHeartNum(0);
        }
        video.setUploadTime(new Date());
        return videoRepository.save(video);
    }

    /**
     * 删除
     * @param videoId videoId
     */
    public void delete(Integer videoId) {
        videoRepository.delete(videoId);
    }

}
