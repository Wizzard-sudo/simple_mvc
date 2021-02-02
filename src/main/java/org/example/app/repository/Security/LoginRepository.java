package org.example.app.repository.Security;

import org.example.web.dto.LoginForm;

import java.util.List;

//TODO пакет секурити с маленькой буквы напишите)) и опять же про дженерики
public interface LoginRepository<T> {

    boolean authenticateUser(LoginForm loginForm);

    List<T> getAllUsers();

    List<T> getAllUsersHidden();

    void addUser(LoginForm user);

    boolean removeUserByName(String name);
}
