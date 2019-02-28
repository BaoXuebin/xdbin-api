package com.xdbin.note.service;

import com.xdbin.note.condition.TodoCondition;
import com.xdbin.note.entity.Todo;
import org.springframework.data.domain.Page;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:27 PM
 */
public interface TodoService {

    Page<Todo> findByPage(TodoCondition todoCondition);

    /**
     * 开始一个待办事项
     * @param title 待办事项标题
     * @param type 待办事项类型
     * @return Todo
     */
    Todo start(String title, String type, Integer ifPub);

    /**
     * 更新待办事项
     * @param title 待办事项标题
     * @param type 待办事项类型
     * @param ifPub 是否公开
     * @return Todo 实体
     */
    Todo update(Long id, String title, String type, Integer ifPub);

    /**
     * 完成一个待办事项
     * @param id todoId
     * @return Todo
     */
    Todo end(Long id);

    /**
     * 删除
     * @param id TodoId
     */
    void delete(Long id);

}
