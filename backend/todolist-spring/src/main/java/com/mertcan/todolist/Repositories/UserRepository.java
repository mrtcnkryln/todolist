package com.mertcan.todolist.Repositories;

import com.mertcan.todolist.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query("SELECT user FROM UserModel user WHERE user.username = ?1 AND user.password = ?2 ")
    UserModel login(String username, String password);

    @Query("SELECT COUNT(user.id) FROM UserModel user WHERE user.username = ?1")
    int isUserExist(String username);

    UserModel findUserById(int id);

}
