package it.unisannio.pcforgeapp.controller;

import it.unisannio.pcforgeapp.model.User;
import it.unisannio.pcforgeapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import org.springframework.security.core.Authentication;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(@RequestParam String username) {
        System.out.println("Ricevuto username da controllare: " + username);
        boolean exists = userService.usernameExists(username);
        System.out.println("Esiste? " + exists);
        return exists ? "true" : "false";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

            userService.registerUser(user);
            return "redirect:/login";


    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "home";
    }
}