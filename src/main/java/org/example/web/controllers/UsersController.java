package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.NonexistentBookException;
import org.example.app.services.Security.LoginService;
import org.example.web.dto.LoginForm;
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
        model.addAttribute("userList", loginService.showAllUsers());
        return "users_page";
    }

    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(name = "usernameToRemove") String usernameToRemove) throws NonexistentBookException {
        if(usernameToRemove.isEmpty()) {
            throw new NonexistentBookException("errorMessageRemoveUsername", "username empty!");
        }else{
            if (loginService.deleteUser(usernameToRemove))
                return "redirect:/users";
            else {
                throw new NonexistentBookException("errorMessageRemoveUsername", "username does not exist");
            }
        }
    }

    @ExceptionHandler(NonexistentBookException.class)
    public String removeBookException(Model model, NonexistentBookException exception){
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("userList", loginService.showAllUsers());
        model.addAttribute(exception.getAttribute(), exception.getMessage());
        return "users_page";
    }
}
