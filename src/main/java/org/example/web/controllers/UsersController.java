package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.Security.LoginService;
import org.example.web.dto.LoginForm;
import org.example.web.dto.remove.UsernameToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    private Logger logger = Logger.getLogger(UsersController.class);
    private LoginService loginService;

    @Autowired
    public UsersController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("usernameToRemove", new UsernameToRemove());
        model.addAttribute("userList", loginService.showAllUsers());
        return "users_page";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid LoginForm loginForm, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            loginService.addUser(loginForm);
            logger.info("user added successfully: " + loginForm);
        }else {
            logger.info("discovered empty fields, the user is not added");
        }
        model.addAttribute("usernameToRemove", new UsernameToRemove());
        model.addAttribute("userList", loginService.showAllUsers());
        return "users_page";
    }

    @PostMapping("/removeUser")
    public String removeUser(@Valid UsernameToRemove usernameToRemove, BindingResult bindingResult, Model model){
        model.addAttribute("loginForm", new LoginForm());
        if(!bindingResult.hasErrors()) {
            loginService.deleteUser(usernameToRemove.getUsername());
            logger.info("deleted user with the name " + usernameToRemove.getUsername());
        }else{
            logger.info("user was not deleted, reason is an empty string");
        }
        model.addAttribute("userList", loginService.showAllUsers());
        return "users_page";
    }
/*
    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(value = "usernameToRemove") String usernameToRemove){
        if( usernameToRemove != "") {
            loginService.deleteUser(usernameToRemove);
            logger.info("deleted user with the name " + usernameToRemove);
        }else{
            logger.info("user was not deleted, reason is an empty string");
        }
        return "redirect:/users";
    }*/

}
