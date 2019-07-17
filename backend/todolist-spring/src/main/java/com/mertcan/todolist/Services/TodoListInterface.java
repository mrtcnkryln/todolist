package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.TodoListModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListInterface {

    TodoListModel createTodoList(TodoListModel todoListModel);

    List<TodoListModel> getAllTodoListByUserId(int userId);

    @Transactional
    @Modifying
    void deleteTodoList(int todoId);
}
