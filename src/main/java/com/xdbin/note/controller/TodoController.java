package com.xdbin.note.controller;

import com.xdbin.annotation.Security;
import com.xdbin.note.condition.TodoCondition;
import com.xdbin.note.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:21 PM
 */
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Security
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        TodoCondition todoCondition = new TodoCondition();
        todoCondition.setPageNo(pageNo);
        todoCondition.setPageSize(pageSize);
        return ResponseEntity.ok(todoService.findByPage(todoCondition));
    }

    @Security
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity start(@RequestParam(value = "title") String title, @RequestParam("type") String type, @RequestParam("ifPub") Integer ifPub) {
        return ResponseEntity.ok(todoService.start(title, type, ifPub));
    }

    @Security
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestParam(value = "title") String title, @RequestParam("type") String type, @RequestParam("ifPub") Integer ifPub) {
        return ResponseEntity.ok(todoService.update(id, title, type, ifPub));
    }

    @Security
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        todoService.delete(id);
        return ResponseEntity.ok(id);
    }
}
