package org.example.app.repository.safety;

import org.example.web.dto.LoginForm;

import java.util.List;

public interface LoginRepository {

    boolean authenticateUser(LoginForm loginForm);

    List<LoginForm> getAllUsers();

    List<LoginForm> getAllUsersHidden();

    void addUser(LoginForm user);

    boolean removeUserByName(String name);
}
