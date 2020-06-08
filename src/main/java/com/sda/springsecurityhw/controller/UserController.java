package com.sda.springsecurityhw.controller;


import com.sda.springsecurityhw.model.User;
import com.sda.springsecurityhw.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users";
    }

    @PostMapping("/userNew")
    public String add(@RequestParam String userName, @RequestParam String role, @RequestParam String email, Model model) {
        User user = new User();
        user.setUserName(userName);
        user.setRole(role);
        user.setEmail(email);
        repository.save(user);
        model.addAttribute("users", repository.findAll());
        return "redirect:/users";
    }

    @PostMapping("/userUpdate/{id}")
    public String update(@PathVariable long id, Model model) {
        User user = repository.findById(id).get();
        if ("ADMIN".equals(user.getRole())) {
            user.setRole("USER");
        }
        else {
            user.setRole("ADMIN");
        }
        repository.save(user);
        model.addAttribute("users", repository.findAll());
        return "redirect:/users";
    }

    @PostMapping("/userDelete/{id}")
    public String delete(@PathVariable long id, Model model) {
        repository.deleteById(id);
        model.addAttribute("users", repository.findAll());
        return "redirect:/users";
    }

}

