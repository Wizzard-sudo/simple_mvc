package org.example.app.services.Security;

import org.example.app.repository.Security.LoginRepository;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginRepository<LoginForm> loginRepo;

    @Autowired
    public LoginService(LoginRepository<LoginForm> loginRepo) {
        this.loginRepo = loginRepo;
    }

    public boolean authenticate(LoginForm loginForm){
        return loginRepo.authenticateUser(loginForm);
    }

    public List<LoginForm> showAllUsers(){
        return loginRepo.getAllUsers();
    }

    public List<LoginForm> showAllUsersHidden(){
        return loginRepo.getAllUsersHidden();
    }

    public boolean addUser(LoginForm user){
        return loginRepo.addUser(user);
    }

    public boolean deleteUser(String name){
        return loginRepo.removeUserByName(name);
    }

}
