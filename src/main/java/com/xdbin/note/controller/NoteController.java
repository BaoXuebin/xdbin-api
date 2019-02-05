package com.xdbin.note.controller;

import com.xdbin.annotation.Security;
import com.xdbin.bean.ErrorBean;
import com.xdbin.common.base.CustomPage;
import com.xdbin.note.condition.NoteCondition;
import com.xdbin.note.entity.Note;
import com.xdbin.note.service.NoteService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:27 PM
 */
@Security
@RestController
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findPubNotes(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       HttpServletRequest request) {
        NoteCondition condition = new NoteCondition();
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);

        String userId = (String) request.getAttribute("_userId");
        CustomPage page =  noteService.findPubFullNotes(userId, condition);
        return ResponseEntity.ok(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody NoteCondition noteCondition, HttpServletRequest request) {
        Assert.notNull(noteCondition, "Note must not be null.");
        Assert.hasLength(noteCondition.getContent(), "Note's content must not be blank.");

        String userId = (String) request.getAttribute("_userId");

        Note note = noteService.saveOrUpdate(userId, noteCondition);
        if (StringUtils.isEmpty(note)) {
            return ResponseEntity.ok(new ErrorBean(500, "Failed to create new note."));
        }
        return ResponseEntity.ok(note);
    }

}
