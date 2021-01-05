package org.example.app.services;

import org.example.web.dto.Book;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginInterface<LoginForm> loginRepo;

    @Autowired
    public LoginService(LoginInterface<LoginForm> loginRepo) {
        this.loginRepo = loginRepo;
    }

    public boolean authenticate(LoginForm loginForm){
        return loginRepo.authenticateUser(loginForm);
    }

    public List<LoginForm> showAllUsers(){
        return loginRepo.getAllUsers();
    }

    public boolean addUser(LoginForm user){
        return loginRepo.addUser(user);
    }

    public boolean deleteUser(String name){
        return loginRepo.removeUserByName(name);
    }

}
