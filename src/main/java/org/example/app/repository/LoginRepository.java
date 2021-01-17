package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.LoginForm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class LoginRepository implements LoginInterface {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LoginForm rootUser = new LoginForm("root", "123", "user");
    private final List<LoginForm> users = new ArrayList(Arrays.asList(rootUser));

    public LoginRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean authenticateUser(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        for (LoginForm user : users)
            if (loginForm.getUsername().equals(user.getUsername()) && loginForm.getPassword().equals(user.getPassword()))
                return true;
        return false;
    }

    @Override
    public List<LoginForm> getAllUsers() {
        List<LoginForm> users = jdbcTemplate.query("SELECT * FROM users", (ResultSet rs, int rowNum) -> {
            LoginForm user = new LoginForm();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        });
        return new ArrayList<>(users);
    }

    @Override
    public List<LoginForm> getAllUsersHidden() {
        List<LoginForm> usersHidden = new ArrayList<>();
        String hiddenPassword = "";
        for (LoginForm user : getAllUsers()) {
            for (int i = 0; i < user.getPassword().length(); i++) {
                hiddenPassword += "*";
            }
            usersHidden.add(new LoginForm(user.getUsername(), hiddenPassword, user.getRole()));
            hiddenPassword = "";
        }
        return usersHidden;
    }

    @Override
    public boolean addUser(LoginForm user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", user.getUsername());
        parameterSource.addValue("password", user.getPassword());
        parameterSource.addValue("role", user.getRole());
        jdbcTemplate.update("INSERT INTO users(username, password, role) VALUES(:username, :password, :role)", parameterSource);
        logger.info("new user added:" + user);
        return true;
    }

    @Override
    public boolean removeUserByName(String userNameToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", userNameToRemove);
        jdbcTemplate.update("DELETE FROM users WHERE username = :username", parameterSource);
        logger.info("remove user completed");
        return true;
    }
}
