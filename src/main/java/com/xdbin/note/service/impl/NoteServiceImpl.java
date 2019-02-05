package com.xdbin.note.service.impl;

import com.xdbin.common.base.CustomPage;
import com.xdbin.common.constants.Constants;
import com.xdbin.common.repository.NativeQueryRepository;
import com.xdbin.note.NoteSql;
import com.xdbin.note.condition.NoteCondition;
import com.xdbin.note.entity.FullNote;
import com.xdbin.note.entity.Note;
import com.xdbin.note.entity.NoteImages;
import com.xdbin.note.repository.NoteImagesRepository;
import com.xdbin.note.repository.NoteRepository;
import com.xdbin.note.service.NoteService;
import com.xdbin.note.vo.FullNoteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:50 PM
 */
@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private NoteImagesRepository noteImagesRepository;

    private NativeQueryRepository nativeQueryRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteImagesRepository noteImagesRepository, NativeQueryRepository nativeQueryRepository) {
        this.noteRepository = noteRepository;
        this.noteImagesRepository = noteImagesRepository;
        this.nativeQueryRepository = nativeQueryRepository;
    }

    @Override
    public CustomPage findPubFullNotes(String userId, NoteCondition noteCondition) {
        Map<String, Object> map = mapCondition(userId, noteCondition);
        CustomPage result = nativeQueryRepository.nativeQueryForPage(
                NoteSql.buildQuerySql(map),
                NoteSql.buildCountSql(map),
                map,
                FullNote.class);
        result.mapEntity(FullNoteVO::from);
        return result;
    }

    @Override
    public void saveOrUpdate(Long noteId, NoteCondition noteCondition) {

    }

    @Override
    public Note saveOrUpdate(String userId, NoteCondition noteCondition) {
        if (StringUtils.isEmpty(noteCondition)) return null;

        /*
         * init note's content and pub.
         */
        Note note = Note.from(noteCondition);
        if (StringUtils.isEmpty(note)) return null;
        note.setPublishTime(new Date());
        note.setUserId(userId);
        note.setValid(Constants.DELETE_FLAG_NORMAL);
        if (StringUtils.isEmpty(noteCondition.getPub()) || Constants.PRI != noteCondition.getPub()) {
            note.setPub(Constants.PUB);
        }
        note = noteRepository.save(note);

        Long noteId = note.getId();

        if (StringUtils.isEmpty(noteCondition.getImages()) || noteCondition.getImages().size() <= 0) {
            /*
               没有图片，直接结束方法
             */
            return note;
        }
        List<NoteImages> noteImages = noteCondition.getImages()
                .stream()
                .map(url -> new NoteImages(noteId, url, Constants.DELETE_FLAG_NORMAL))
                .collect(Collectors.toList());
        noteImagesRepository.save(noteImages);
        return note;
    }

    /*
     * private function.
     */
    private Map<String, Object> mapCondition(String userId, NoteCondition condition) {
        if (StringUtils.isEmpty(condition)) return null;
        return new HashMap<String, Object>() {{
            put("pageNo", condition.getPageNo());
            put("pageSize", condition.getPageSize());
            put("pubOrUserId", userId);
        }};
    }

}
