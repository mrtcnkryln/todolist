package com.mertcan.todolist.Services;

import com.mertcan.todolist.Models.UserModel;
import com.mertcan.todolist.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserInterface {

    private final UserRepository mUserRepository;

    public UserService(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public UserModel login(String username, String password) {
        return mUserRepository.login(username,password);
    }

    @Override
    public UserModel register(UserModel userModel) {
        return mUserRepository.save(userModel);
    }

    @Override
    public UserModel findUserById(int id) {
        return mUserRepository.findUserById(id);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return mUserRepository.findAll();
    }

    @Override
    public int isUserExist(String username) {
        return mUserRepository.isUserExist(username);
    }
}
