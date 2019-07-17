package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.TodoListModel;
import com.mertcan.todolist.Repositories.TodoListRepository;
import com.mertcan.todolist.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService implements TodoListInterface {

    private final TodoListRepository mTodoListRepository;

    public TodoListService(TodoListRepository todoListRepository) {
        mTodoListRepository = todoListRepository;
    }

    @Override
    public TodoListModel createTodoList(TodoListModel todoListModel) {
        return mTodoListRepository.save(todoListModel);
    }

    @Override
    public List<TodoListModel> getAllTodoListByUserId(int userId) {
        return mTodoListRepository.getAllTodoListFromUserId(userId);
    }

    @Override
    public void deleteTodoList(int todoId) {
        mTodoListRepository.deleteById(todoId);
    }

}
