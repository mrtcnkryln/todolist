package com.mertcan.todolist.Repositories;

import com.mertcan.todolist.Models.DependencyModel;
import com.mertcan.todolist.Models.TodoListItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DependencyRepository extends JpaRepository<DependencyModel, Integer> {


    List<DependencyModel> findByItemFrom(TodoListItemModel itemFrom);

    @Query("SELECT COUNT(dependecy.id) FROM  DependencyModel dependecy WHERE dependecy.itemFrom.id =?1 AND dependecy.itemTo.state=0")
    int isItemDependent(int itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DependencyModel dependecy WHERE dependecy.itemFrom.id = ?1 OR dependecy.itemTo.id=?1")
    void deleteAllDependencies(int itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DependencyModel dependecy WHERE dependecy.itemFrom.todoList.id = ?1 OR dependecy.itemTo.todoList.id=?1")
    void deleteAllDependenciesByTodoListId(int listId);
}
