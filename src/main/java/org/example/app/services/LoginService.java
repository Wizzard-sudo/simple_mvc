package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginInterface<LoginForm> loginRepo;
    private Logger logger = Logger.getLogger(LoginService.class);

    public LoginService(LoginInterface<LoginForm> loginRepo) {
        this.loginRepo = loginRepo;
    }

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        List<LoginForm> users = loginRepo.getAllUsers();
        for(LoginForm user: users)
            if (loginForm.getUsername().equals(user.getUsername()) && loginForm.getPassword().equals(user.getPassword()))
                return true;
            return false;
    }
}
