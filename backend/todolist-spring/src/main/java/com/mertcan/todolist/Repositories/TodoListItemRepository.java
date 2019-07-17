package com.mertcan.todolist.Repositories;

import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Models.TodoListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListItemRepository extends JpaRepository<TodoListItemModel, Integer> {

    @Query("SELECT todoListItem FROM TodoListItemModel todoListItem WHERE todoListItem.todoList.id = ?1")
    List<TodoListItemModel> getAllItemsFromTodoListId(int todoListId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TodoListItemModel todoListItem WHERE todoListItem.todoList.id = ?1")
    void deleteAllTodoItemsFromListId(int listId);

    @Query("SELECT todoListItem FROM TodoListItemModel todoListItem WHERE todoListItem.id  NOT IN (SELECT dependencyModel.itemTo.id FROM  com.mertcan.todolist.Models.DependencyModel dependencyModel WHERE dependencyModel.itemFrom.id =?1) AND todoListItem.todoList.id=?2 AND todoListItem.id <> ?1")
    List<TodoListItemModel> getAllNotDependentTodoItems(int itemId, int listId);

    TodoListItemModel findById(int id);


}
