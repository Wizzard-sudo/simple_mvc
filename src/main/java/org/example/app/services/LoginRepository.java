package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class LoginRepository implements LoginInterface{

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final LoginForm rootUser = new LoginForm("root", "123");
    private final List<LoginForm> users = new ArrayList(Arrays.asList(rootUser));
    //private final List<LoginForm> users = new ArrayList<>();

    @Override
    public boolean authenticateUser(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        for(LoginForm user: users)
            if (loginForm.getUsername().equals(user.getUsername()) && loginForm.getPassword().equals(user.getPassword()))
                return true;
        return false;
    }

    @Override
    public List<LoginForm> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public List<LoginForm> getAllUsersHidden() {
        List <LoginForm> usersHidden = new ArrayList<>();
        String hiddenPassword = "";
        for(LoginForm user : users) {
            for (int i = 0; i < user.getPassword().length(); i++) {
                hiddenPassword += "*";
            }

            usersHidden.add(new LoginForm(user.getUsername(), hiddenPassword));
            hiddenPassword = "";
        }
        return usersHidden;
    }

    @Override
    public boolean addUser(LoginForm user) {
        logger.info("new user added:" + user);
        return users.add(user);
    }

    @Override
    public boolean removeUserByName(String userNameToRemove) {
        for(LoginForm user : getAllUsers())
            if(user.getUsername().equals(userNameToRemove)){
                logger.info("remove user completed: " + user);
                return users.remove(user);
            }
        return false;
    }
}
