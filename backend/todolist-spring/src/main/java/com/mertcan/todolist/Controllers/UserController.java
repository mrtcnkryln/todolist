package com.mertcan.todolist.Controllers;

import com.mertcan.todolist.Models.ResponseJsonModel;
import com.mertcan.todolist.Models.UserModel;
import com.mertcan.todolist.Services.UserInterface;
import com.mertcan.todolist.Utils.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = Constants.BASE_URL + Constants.USER_URL;
    private final UserInterface mUserService;

    public UserController(UserInterface mUserService) {
        this.mUserService = mUserService;
    }

    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseJsonModel loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        ResponseJsonModel response;
        UserModel userModel = mUserService.login(username, password);
        if (userModel != null) {
            List<Object> list = new ArrayList<>();
            list.add(userModel);
            response = new ResponseJsonModel(true, "Success login", list);
        } else {
            response = new ResponseJsonModel(false, "user not found");
        }
        return response;
    }


    @CrossOrigin(origins = Constants.CROSS_ORIGIN_PORT)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseJsonModel registerUser(@RequestBody UserModel userModel) {
        ResponseJsonModel response;
        if (mUserService.isUserExist(userModel.getUsername()) == 0) {
            UserModel user = mUserService.register(userModel);
            if (user != null) {
                List<Object> list = new ArrayList<>();
                list.add(user);
                response = new ResponseJsonModel(true, "Register successful", list);
            } else {
                response = new ResponseJsonModel(false, "Register failed");
            }
        } else {
            response = new ResponseJsonModel(false, "Email exist");
        }
        return response;
    }
}
