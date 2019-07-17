package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.DependencyModel;
import com.mertcan.todolist.Models.TodoListItemModel;

import java.util.List;

public interface DependencyInterface {
    List<DependencyModel> createDependency(DependencyModel dependencyModel);

    void deleteAllDependencies(int itemId);

    void deleteAllDependenciesByListId(int listId);

    List<DependencyModel> findTodoItemDependencies(TodoListItemModel todoItem);

    void deleteDependency(int itemId);

    int isTodoItemDependent(int itemId);
}
