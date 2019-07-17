package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Repositories.TodoListItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListItemService implements TodoListItemInterface {

    private final TodoListItemRepository mTodoListItemRepository;


    public TodoListItemService(TodoListItemRepository todoListItemRepository) {
        mTodoListItemRepository = todoListItemRepository;
    }

    @Override
    public List<TodoListItemModel> getAllTodoItemsByListId(int todoId, int state) {
        return mTodoListItemRepository.getAllItemsFromTodoListId(todoId);
    }

    @Override
    public List<TodoListItemModel> getAllNotDependentTodoItems(int itemId, int listId) {
        return mTodoListItemRepository.getAllNotDependentTodoItems(itemId, listId);
    }

    @Override
    public TodoListItemModel createTodoItem(TodoListItemModel todoItemModel) {
        return mTodoListItemRepository.save(todoItemModel);
    }

    @Override
    public void deleteAllTodoItems(int todoListId) {
        mTodoListItemRepository.deleteAllTodoItemsFromListId(todoListId);
    }

    @Override
    public void deleteTodoItem(int itemId) {
        mTodoListItemRepository.deleteById(itemId);
    }

    @Override
    public TodoListItemModel updateItemState(int itemId, int state) {
        TodoListItemModel todoListItemModel = mTodoListItemRepository.findById(itemId);
        todoListItemModel.setState(state);
        return mTodoListItemRepository.save(todoListItemModel);
    }
}
