package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("userList", loginService.showAllUsers());
        return "users_page";
    }


    @PostMapping("/addUser")
    public String addUser(LoginForm loginForm){
        if(loginForm.getUsername() != "" && loginForm.getPassword() != ""){
            loginService.addUser(loginForm);
            logger.info("user added successfully: " + loginForm);
        }else{
            logger.info("discovered empty fields, the user is not added");
        }
        return "redirect:/users";
    }

    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(value = "usernameToRemove") String usernameToRemove){
        if( usernameToRemove != "") {
            loginService.deleteUser(usernameToRemove);
            logger.info("deleted user with the name " + usernameToRemove);
        }else{
            logger.info("user was not deleted, reason is an empty string");
        }
        return "redirect:/users";
    }

}
