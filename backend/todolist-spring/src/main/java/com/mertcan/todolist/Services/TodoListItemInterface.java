package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.TodoListItemModel;

import java.util.List;

public interface TodoListItemInterface {

    List<TodoListItemModel> getAllTodoItemsByListId(int todoId, int state);

    List<TodoListItemModel> getAllNotDependentTodoItems(int itemId,int listId);

    TodoListItemModel createTodoItem(TodoListItemModel todoItemModel);

    void deleteAllTodoItems(int todoId);

    void deleteTodoItem(int itemId);

    TodoListItemModel updateItemState(int itemId, int state);
}
