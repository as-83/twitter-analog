package com.abdsul.sweeter.controller;

import com.abdsul.sweeter.entity.Message;
import com.abdsul.sweeter.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping(path = "/message")
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greet( Model model){

        model.addAttribute("hello", "Hello, user!");
        return "greeting";
    }


    @GetMapping("/main")
    public String main( Model model){

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Model model){
        Message message = new Message(text, tag);
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model){


       Iterable<Message> messages;
       if(filter != null && !filter.isEmpty()) {
           messages = messageRepo.findByTag(filter);
       }else {
           messages = messageRepo.findAll();
       }

        model.addAttribute("messages", messages);

        return "main";
    }

}
