package org.example.web.dto;

import org.hibernate.validator.constraints.Length;

public class LoginForm {

    @Length(min = 2)
    private String username;
    @Length(min = 5)
    private String password;
    @Length(min = 3)
    private String role;

    public LoginForm(){

    }
    public LoginForm(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
    }
