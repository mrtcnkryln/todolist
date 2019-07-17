package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.UserModel;

import java.util.List;

public interface UserInterface {

    UserModel login(String username, String password);

    UserModel register(UserModel userModel);

    UserModel findUserById(int id);

    List<UserModel> getAllUsers();

    int isUserExist(String username);
}
