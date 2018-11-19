package com.xdbin.lab.controller;

import com.xdbin.annotation.Security;
import com.xdbin.lab.condition.MoodCondition;
import com.xdbin.lab.service.MoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mood")
public class MoodController {

    @Resource
    private MoodService moodService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity queryByPage(@RequestParam(value = "page", required = false) Integer page) {
        if (StringUtils.isEmpty(page)) page = 1;
        return ResponseEntity.ok(moodService.findMoodsByPage(page, 20));
    }

    @Security
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody MoodCondition moodCondition) {
        return ResponseEntity.ok(moodService.save(moodCondition));
    }

}
