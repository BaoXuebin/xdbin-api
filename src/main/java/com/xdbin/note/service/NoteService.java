package com.xdbin.note.service;

import com.xdbin.common.base.CustomPage;
import com.xdbin.note.condition.NoteCondition;
import com.xdbin.note.entity.Note;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:48 PM
 */
public interface NoteService {

    CustomPage findPubFullNotes(String userId, NoteCondition noteCondition);

    // TODO: 修改返回值
    void saveOrUpdate(Long noteId, NoteCondition noteCondition);

    Note saveOrUpdate(NoteCondition noteCondition);

}
