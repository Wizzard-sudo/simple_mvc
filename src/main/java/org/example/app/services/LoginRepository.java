package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class LoginRepository implements LoginInterface{

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final LoginForm rootUser = new LoginForm("root", "123");
    private final List<LoginForm> users = Arrays.asList(rootUser);

    @Override
    public List<LoginForm> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void addUser(LoginForm user) {
        logger.info("new user added:" + user);
        users.add(user);
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
