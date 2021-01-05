package org.example.app.services;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface LoginInterface<T> {

    boolean authenticateUser(LoginForm loginForm);

    List<T> getAllUsers();

    boolean addUser(LoginForm user);

    boolean removeUserByName(String name);
}
