package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.services.safety.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("userList", loginService.showAllUsersHidden());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if(loginService.authenticate(loginForm)){
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        }else{
            logger.info("login FAIL redirect to login");
            throw new BookShelfLoginException("invalid username or password");
        }
    }

    @PostMapping("/addUser")
    public String addUser(LoginForm loginForm){
        if(loginForm.getUsername().equals("") && loginForm.getPassword().equals("")){
            loginService.addUser(loginForm);
            logger.info("user added successfully: " + loginForm);
        }else{
            logger.info("discovered empty fields, the user is not added");
        }
        return "redirect:/login";
    }

    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(value = "usernameToRemove") String usernameToRemove){
        if( usernameToRemove.equals("")) {
            loginService.deleteUser(usernameToRemove);
            logger.info("deleted user with the name " + usernameToRemove);
        }else{
            logger.info("user was not deleted, reason is an empty string");
        }
        return "redirect:/login";
    }


    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }

}
