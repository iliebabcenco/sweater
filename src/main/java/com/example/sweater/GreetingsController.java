package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingsController {

    private final MessageRepo messageRepo;

    @Autowired
    public GreetingsController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false,
                    defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Model model) {

        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Model model) {
        messageRepo.save(new Message(text, tag));
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/filter")
    public String filter (@RequestParam String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepo.findByTag(filter));
        } else {
            model.addAttribute("messages", messageRepo.findAll());
        }

        return "main";
    }

}