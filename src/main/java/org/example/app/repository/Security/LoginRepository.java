package org.example.app.repository.Security;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface LoginRepository<T> {

    boolean authenticateUser(LoginForm loginForm);

    List<T> getAllUsers();

    List<T> getAllUsersHidden();

    boolean addUser(LoginForm user);

    boolean removeUserByName(String name);
}
