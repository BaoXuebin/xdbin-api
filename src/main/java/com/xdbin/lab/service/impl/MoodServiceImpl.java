package com.xdbin.lab.service.impl;

import com.xdbin.common.constants.Constants;
import com.xdbin.lab.condition.MoodCondition;
import com.xdbin.lab.entity.Mood;
import com.xdbin.lab.repository.MoodRepository;
import com.xdbin.lab.service.MoodService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MoodServiceImpl implements MoodService {

    @Resource
    private MoodRepository moodRepository;

    @Override
    public Page<Mood> findMoodsByPage(int pageNo, int pageSize) {
        // format
        pageNo = pageNo <= 0 ? 1 : pageNo;
        pageSize = pageSize < 0 ? 10 : pageSize;
        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        return moodRepository.findAll(new PageRequest(pageNo - 1, pageSize, s));
    }

    @Override
    public Mood save(MoodCondition moodCondition) {
        Mood mood = Mood.from(moodCondition);
        Assert.notNull(mood, "Mood Entity is not null!");

        mood.setPublishTime(new Date());
        mood.setPub(StringUtils.isEmpty(mood.getPub()) ? Constants.PUB : Constants.PRI);
        mood.setValid(StringUtils.isEmpty(mood.getValid()) ? Constants.DELETE_FLAG_NORMAL : Constants.DELETE_FLAG_DELETE);

        return moodRepository.save(mood);
    }


}
