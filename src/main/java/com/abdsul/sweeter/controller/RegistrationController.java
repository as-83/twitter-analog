package com.abdsul.sweeter.controller;

import com.abdsul.sweeter.entity.User;
import com.abdsul.sweeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String register(User user, Model model){

        if(!userService.addUser(user)){
            model.addAttribute("message", "User with that name already exists");
            return  "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("message", "Successfully activated!");
        }else{
            model.addAttribute("message", "Activation code not found!");
        }


        return "login";
    }

}
