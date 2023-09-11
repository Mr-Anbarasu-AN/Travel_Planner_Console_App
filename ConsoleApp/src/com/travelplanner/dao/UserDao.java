package com.travelplanner.dao;

import com.travelplanner.model.User;

import java.util.List;

//Abstraction
//Defining a set of methods without specifying their implementations
//Hiding internal details and showing functionality is known as abstraction
public interface UserDao {
    void createAdmin(User admin);
    void createUser(User user);
    User getAdminByEmail(String email);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int userId);
}


/*
*/


/*
package com.travelplanner.dao;

import com.travelplanner.model.User;

import java.util.List;

public interface UserDao {
    public void createUser(User user);
    public User getUserByEmail(String email);
    public List<User> getAllUsers();
    public void updateUser(User user);
    public void deleteUser(int userId);
}


/*

*/