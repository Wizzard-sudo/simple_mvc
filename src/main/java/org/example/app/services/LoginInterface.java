package org.example.app.services;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface LoginInterface<T> {

    List<T> getAllUsers();

    void addUser(LoginForm user);

    boolean removeUserByName(String name);
}
