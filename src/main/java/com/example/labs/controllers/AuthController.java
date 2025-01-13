package com.example.labs.controllers;

import com.example.labs.models.User;
import com.example.labs.repo.UserRepository;
import com.example.labs.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/products";
    }
    @ModelAttribute
    public void addAttributes(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("user", authentication.getName());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, BindingResult result, Model model) {
        // Проверяем наличие ошибок
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register"; // Возвращаемся на страницу регистрации
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("error","Пароли не совпадают");
            model.addAttribute("user", user); // Вернуть введенные данные пользователю
            return "register";
        }

        // Регистрируем пользователя
        userService.registerUser(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getConfirmPassword()

        );

        // Перенаправляем на страницу логина после успешной регистрации
        return "redirect:/login";
    }

}
