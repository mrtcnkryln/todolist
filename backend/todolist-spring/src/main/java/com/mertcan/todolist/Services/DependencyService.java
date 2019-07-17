package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.DependencyModel;
import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Repositories.DependencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependencyService implements DependencyInterface {

    private DependencyRepository mDependencyRepository;

    public DependencyService(DependencyRepository dependencyRepository) {
        mDependencyRepository = dependencyRepository;
    }

    @Override
    public List<DependencyModel> createDependency(DependencyModel dependencyModel) {
        mDependencyRepository.save(dependencyModel);
        return mDependencyRepository.findAll();
    }

    @Override
    public void deleteAllDependencies(int itemId) {
        mDependencyRepository.deleteAllDependencies(itemId);
    }

    @Override
    public void deleteAllDependenciesByListId(int listId) {
        mDependencyRepository.deleteAllDependenciesByTodoListId(listId);
    }

    @Override
    public List<DependencyModel> findTodoItemDependencies(TodoListItemModel itemFrom) {
        return mDependencyRepository.findByItemFrom(itemFrom);
    }


    @Override
    public void deleteDependency(int itemId) {
        mDependencyRepository.deleteById(itemId);
    }

    @Override
    public int isTodoItemDependent(int itemId) {
        return mDependencyRepository.isItemDependent(itemId);
    }
}
