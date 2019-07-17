package com.mertcan.todolist.Controllers;

import com.mertcan.todolist.Models.DependencyModel;
import com.mertcan.todolist.Models.ResponseJsonModel;
import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Services.DependencyInterface;
import com.mertcan.todolist.Services.TodoListItemInterface;
import com.mertcan.todolist.Utils.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(TodoListItemController.BASE_URL)
public class TodoListItemController {

    public static final String BASE_URL = Constants.BASE_URL + Constants.TODOITEM_URL;
    private final TodoListItemInterface mTodoItemService;
    private final DependencyInterface mDependencyService;

    public TodoListItemController(TodoListItemInterface mTodoItemService, DependencyInterface mDependencyService) {
        this.mTodoItemService = mTodoItemService;
        this.mDependencyService = mDependencyService;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseJsonModel createTodoItem(@RequestBody TodoListItemModel todoItemModel) {
        ResponseJsonModel response;
        TodoListItemModel todoItem = mTodoItemService.createTodoItem(todoItemModel);
        if (todoItem != null) {
            List<Object> list = new ArrayList<>(mTodoItemService.getAllTodoItemsByListId(todoItem.getTodoList().getId(),-1));
            response = new ResponseJsonModel(true, "Todo item created", list);
        } else {
            response = new ResponseJsonModel(false, "A problem occurred");
        }
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "mark")
    public ResponseJsonModel markTodoItem(@RequestParam("listId") int listId, @RequestParam("itemId") int itemId,@RequestParam("state") int state) {
        ResponseJsonModel response;

        if (mDependencyService.isTodoItemDependent(itemId) == 0 || state == 1) {
            int updatedState = state == 1 ? 0 : 1;
            TodoListItemModel todoItem = mTodoItemService.updateItemState(itemId, updatedState);
            if (todoItem != null) {
                List<Object> list = new ArrayList<>(mTodoItemService.getAllTodoItemsByListId(listId, -1));
                response = new ResponseJsonModel(true, list);
            } else {
                response = new ResponseJsonModel(false, "A problem occured");
            }
        } else {
            response = new ResponseJsonModel(false, "Dependencies are must be checked");

        }

        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @GetMapping(value = "list/{listId}")
    public ResponseJsonModel getAllTodoListOfUser(@PathVariable int listId) {
        List<Object> list = new ArrayList<>(mTodoItemService.getAllTodoItemsByListId(listId, -1));
        ResponseJsonModel response = new ResponseJsonModel(true, list);
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @GetMapping(value = "notDependent/{itemId}/{listId}")
    public ResponseJsonModel getAllNotDependentTodoListOfUser(@PathVariable int itemId, @PathVariable int listId) {
        List<Object> list = new ArrayList<>(mTodoItemService.getAllNotDependentTodoItems(itemId, listId));
        ResponseJsonModel response = new ResponseJsonModel(true, list);
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @DeleteMapping(value = "delete/{itemId}/{listId}")
    public ResponseJsonModel deleteTodoItem(@PathVariable("itemId") int id, @PathVariable("listId") int listId) {
        ResponseJsonModel response;
        try {
            mDependencyService.deleteAllDependencies(id);
            mTodoItemService.deleteTodoItem(id);
            List<Object> list = new ArrayList<>(mTodoItemService.getAllTodoItemsByListId(listId, -1));
            response = new ResponseJsonModel(true, "Success", list);
        } catch (Exception e) {
            response = new ResponseJsonModel(false, e.getMessage());
        }
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @GetMapping(value = "dependency/{itemId}")
    public ResponseJsonModel getAllDependenciesOfTodoItem(@PathVariable int itemId) {
        TodoListItemModel model = new TodoListItemModel();
        model.setId(itemId);
        List<Object> list = new ArrayList<>(mDependencyService.findTodoItemDependencies(model));

        ResponseJsonModel response = new ResponseJsonModel(true, list);
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "dependency/create", method = RequestMethod.POST)
    public ResponseJsonModel createDependency(@RequestBody DependencyModel dependencyModel) {
        ResponseJsonModel response;
        List<DependencyModel> dependencyModelList = mDependencyService.createDependency(dependencyModel);
        if (dependencyModelList != null) {
            List<Object> list = new ArrayList<>(dependencyModelList);
            response = new ResponseJsonModel(true, "Todo List item created ", list);
        } else {
            response = new ResponseJsonModel(false, "Failed");
        }
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @DeleteMapping(value = "delete/dependency/{dependencyId}/{itemId}")
    public ResponseJsonModel deleteDependency(@PathVariable("dependencyId") int dependencyId, @PathVariable("itemId") int itemId) {
        ResponseJsonModel response;
        try {
            mDependencyService.deleteDependency(dependencyId);
            TodoListItemModel todoItemModel = new TodoListItemModel();
            todoItemModel.setId(itemId);
            List<Object> list = new ArrayList<>(mDependencyService.findTodoItemDependencies(todoItemModel));
            response = new ResponseJsonModel(true, "Success", list);
        } catch (Exception e) {
            response = new ResponseJsonModel(false, "A problem occurred");
        }
        return response;
    }
}
