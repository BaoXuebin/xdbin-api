package com.xdbin.note.repository;

import com.xdbin.note.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:22 PM
 */
public interface TodoRepository  extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
}
