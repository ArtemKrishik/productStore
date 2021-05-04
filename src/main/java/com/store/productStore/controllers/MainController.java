package com.store.productStore.controllers;



import com.store.productStore.models.User;
import com.store.productStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting( Model model) {
        Iterable<User> users=userRepository.findAll();
        model.addAttribute("users", users);
        return "greeting";
    }

    @GetMapping("/addPage")
    public String addPage( Model model) {

        return "addPage";
    }

    @PostMapping("/addPage")
    public String add(@RequestParam String name, @RequestParam String password, Model model) {
        User user=new User(name, password);
        userRepository.save(user);
        return "redirect:/greeting";
    }
}
