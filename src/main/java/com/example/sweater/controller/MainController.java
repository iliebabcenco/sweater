package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final MessageRepo messageRepo;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String home(Model model) {
        return "home";
    }

    @GetMapping("main")
    public String main(Model model) {


        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        messageRepo.save(new Message(text, tag, user));
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("filter")
    public String filter (@RequestParam String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepo.findByTag(filter));
        } else {
            model.addAttribute("messages", messageRepo.findAll());
        }

        return "main";
    }

}