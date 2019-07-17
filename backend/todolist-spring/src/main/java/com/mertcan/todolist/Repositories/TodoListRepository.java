package com.mertcan.todolist.Repositories;

import com.mertcan.todolist.Models.TodoListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoListModel, Integer> {

    @Query("SELECT todoList FROM TodoListModel todoList WHERE todoList.user.id = ?1")
    List<TodoListModel> getAllTodoListFromUserId(int userId);


}
