package com.example.carinventory.controller;

import com.example.carinventory.dto.UserDTO;
import com.example.carinventory.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailsService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model,@ModelAttribute UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
        } catch (Exception e) {
            log.error("Cannot register user {}", userDTO, e);
            model.addAttribute("errorMessage", "Username already present");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("userDTO", new UserDTO());
        if (error != null) {
            model.addAttribute("errorMessage", "User not found");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
        return "redirect:/login?logout=true";
    }
}
