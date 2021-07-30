package com.example.sweater.controller;


import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserRepo repository;

    @Autowired
    public RegistrationController(UserRepo repository) {
        this.repository = repository;
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User foundUser = repository.findByUsername(user.getUsername());
        if (foundUser != null) {
            model.addAttribute("message", "Users exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        repository.save(user);

        return "redirect:/login";
    }

}
