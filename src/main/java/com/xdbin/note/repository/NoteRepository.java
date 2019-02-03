package com.xdbin.note.repository;

import com.xdbin.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:43 PM
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
}
