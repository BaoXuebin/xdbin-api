package com.xdbin.note.service.impl;

import com.xdbin.common.constants.Constants;
import com.xdbin.note.condition.TodoCondition;
import com.xdbin.note.entity.Todo;
import com.xdbin.note.repository.TodoRepository;
import com.xdbin.note.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:27 PM
 */
@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Page<Todo> findByPage(TodoCondition todoCondition) {
        log.info("Query condition: {}", todoCondition.toString());
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return todoRepository.findAll(where(todoCondition), new PageRequest(todoCondition.getPageNo() - 1, todoCondition.getPageSize(), sort));
    }

    @Override
    public Todo start(String title, String type, Integer ifPub) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setType(type);
        todo.setIfPub(ifPub);
        todo.setCreateTime(new Date());
        todo.setValid(Constants.DELETE_FLAG_NORMAL);
        log.info("Save Todo Entity: {}", todo.toString());
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, String title, String type, Integer ifPub) {
        Todo todo = todoRepository.findOne(id);
        if (StringUtils.isEmpty(todo)) {
            return null;
        }
        todo.setTitle(title);
        todo.setType(type);
        todo.setIfPub(ifPub);
        todo.setCreateTime(new Date());
        log.info("Update Todo Entity: {}", todo.toString());
        return todoRepository.save(todo);
    }

    @Override
    public Todo end(Long id) {
        Todo todo = todoRepository.findOne(id);
        if (StringUtils.isEmpty(todo) || !StringUtils.isEmpty(todo.getIfFinish()) || todo.getIfFinish() == Constants.DELETE_FLAG_NORMAL) {
            return null;
        }
        todo.setFinishTime(new Date());
        todo.setIfFinish(Constants.DELETE_FLAG_NORMAL);
        log.info("End Todo: {}", todo.toString());
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete Todo By Id: {}", id);
        todoRepository.delete(id);
    }

    /*
     * private function.
     */
    private Specification<Todo> where(TodoCondition todoCondition) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(todoCondition.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + todoCondition.getTitle() + "%"));
            }
            if (!StringUtils.isEmpty(todoCondition.getType())) {
                predicates.add(criteriaBuilder.like(root.get("type").as(String.class), todoCondition.getType()));
            }
            if (!StringUtils.isEmpty(todoCondition.getCreateTimeStart())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), todoCondition.getCreateTimeStart()));
            }
            if (!StringUtils.isEmpty(todoCondition.getCreateTimeEnd())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), todoCondition.getCreateTimeEnd()));
            }
            if (!StringUtils.isEmpty(todoCondition.getFinishTimeStart())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("finishTime").as(Date.class), todoCondition.getFinishTimeStart()));
            }
            if (!StringUtils.isEmpty(todoCondition.getFinishTimeEnd())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("finishTime").as(Date.class), todoCondition.getFinishTimeEnd()));
            }
            if (!StringUtils.isEmpty(todoCondition.getIfPub())) {
                predicates.add(criteriaBuilder.equal(root.get("ifPub").as(Integer.class), todoCondition.getIfPub()));
            }

            CriteriaQuery cq = criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
            return cq.getRestriction();
        };
    }
}
