package com.mertcan.todolist;

import com.mertcan.todolist.Models.ResponseJsonModel;
import com.mertcan.todolist.Models.TodoListItemModel;
import com.mertcan.todolist.Models.TodoListModel;
import com.mertcan.todolist.Models.UserModel;
import com.mertcan.todolist.Services.TodoListInterface;
import com.mertcan.todolist.Services.UserInterface;
import com.mertcan.todolist.Utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UnitTest extends AbstractTest {

    private UserModel mUser;

    @Autowired
    private UserInterface mUserService;

    @Autowired
    private TodoListInterface mTodoListService;


    @Override
    @Before
    public void setUp() {
        super.setUp();
        mUser = new UserModel();
        mUser.setFullName("Mertcan Karayılan");
        mUser.setUsername("mrtcnkryln");
        mUser.setPassword("123456");
        mUserService.register(mUser);

        TodoListModel todoListFirst = new TodoListModel();
        todoListFirst.setName("first todo");
        todoListFirst.setUser(mUser);
        mTodoListService.createTodoList(todoListFirst);

        TodoListModel todoListSecond = new TodoListModel();
        todoListSecond.setName("second todo");
        todoListSecond.setUser(mUser);
        mTodoListService.createTodoList(todoListSecond);

    }


    @Test
    public void createItemToList() throws Exception {
        String uri = Constants.BASE_URL + Constants.TODOITEM_URL + "/create";
        TodoListItemModel itemModel = new TodoListItemModel();
        itemModel.setName("do something");
        itemModel.setDescription("do something today. don't forget");
        itemModel.setDeadline(new Date());
        itemModel.setTodoList(mTodoListService.getAllTodoListByUserId(mUser.getId()).get(0));

        String inputJson = super.mapToJson(itemModel);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResponseJsonModel response = super.mapFromJson(content, ResponseJsonModel.class);
        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void deleteTodoList() throws Exception {
        String uri = Constants.BASE_URL + Constants.TODOLIST_URL  + "/delete/" + mTodoListService.getAllTodoListByUserId(mUser.getId()).get(0).getId() + "/" + mUser.getId();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResponseJsonModel response = super.mapFromJson(content, ResponseJsonModel.class);
        assertEquals(response.isSuccess(), true);
    }

    @Test
    public void getDependencies() throws Exception {
        String uri = Constants.BASE_URL + Constants.TODOITEM_URL  + "/dependency/2";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResponseJsonModel response = super.mapFromJson(content, ResponseJsonModel.class);
        assertEquals(response.isSuccess(), true);
    }
}
