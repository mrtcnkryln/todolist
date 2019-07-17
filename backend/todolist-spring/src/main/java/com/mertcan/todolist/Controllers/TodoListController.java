package com.mertcan.todolist.Controllers;

import com.mertcan.todolist.Models.ResponseJsonModel;
import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Models.TodoListModel;
import com.mertcan.todolist.Services.DependencyInterface;
import com.mertcan.todolist.Services.TodoListInterface;
import com.mertcan.todolist.Services.TodoListItemInterface;
import com.mertcan.todolist.Services.UserInterface;
import com.mertcan.todolist.Utils.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(TodoListController.BASE_URL)
public class TodoListController {

    public static final String BASE_URL = Constants.BASE_URL + Constants.TODOLIST_URL;

    private final TodoListInterface mTodoListService;
    private final TodoListItemInterface mTodoItemService;
    private final DependencyInterface mDependencyService;

    public TodoListController(TodoListInterface mTodoListService, TodoListItemInterface mTodoItemService, DependencyInterface mDependencyService) {
        this.mTodoListService = mTodoListService;
        this.mTodoItemService = mTodoItemService;
        this.mDependencyService = mDependencyService;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @GetMapping(value = "user/{userId}")
    public ResponseJsonModel getAllTodoListOfUser(@PathVariable int userId) {
        List<Object> list = new ArrayList<>(mTodoListService.getAllTodoListByUserId(userId));
        ResponseJsonModel response = new ResponseJsonModel(true, list);
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseJsonModel createTodoList(@RequestBody TodoListModel todoListModel) {
        ResponseJsonModel response;
        TodoListModel todoList = mTodoListService.createTodoList(todoListModel);
        if (todoList != null) {
            List<Object> list = new ArrayList<>(mTodoListService.getAllTodoListByUserId(todoListModel.getUser().getId()));
            response = new ResponseJsonModel(true, "Todo list created", list);
        } else {
            response = new ResponseJsonModel(false, "A problem occurred");
        }
        return response;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @DeleteMapping(value = "delete/{listId}/{userId}")
    public ResponseJsonModel deleteTodoList(@PathVariable("listId") int id,@PathVariable("userId") int userId) {

        ResponseJsonModel response;
        try {
            //mDependencyService.deleteAllDependenciesByListId(id);
            for (TodoListItemModel item : mTodoItemService.getAllTodoItemsByListId(id,-1)) {
                mDependencyService.deleteAllDependencies(item.getId());
            }
            mTodoItemService.deleteAllTodoItems(id);
            mTodoListService.deleteTodoList(id);
            List<Object> list = new ArrayList<>(mTodoListService.getAllTodoListByUserId(
                    userId));
            response = new ResponseJsonModel(true, "Success",list);
        }catch (Exception e){
            response = new ResponseJsonModel(false, e.getMessage());
        }
        return response;
    }
}
