package com.xdbin.lab.service;

import com.xdbin.lab.condition.MoodCondition;
import com.xdbin.lab.entity.Mood;
import org.springframework.data.domain.Page;

public interface MoodService {

    Page<Mood> findMoodsByPage(int pageNo, int pageSize);

    Page<Mood> findAllMoodsByPage(int pageNo, int pageSize);

    Mood save(MoodCondition moodCondition);

}
