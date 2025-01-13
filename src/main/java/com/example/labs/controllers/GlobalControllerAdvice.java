package com.example.labs.controllers;

import com.example.labs.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    // Метод для добавления атрибута isAdmin в модель для всех страниц
    @ModelAttribute
    public void addIsAdminToModel(Model model) {
        boolean isAdmin = false;

        // Получаем информацию о текущем аутентифицированном пользователе
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // Проверяем, является ли пользователь администратором
            isAdmin = auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }

        // Добавляем атрибут в модель, чтобы он был доступен на всех страницах
        model.addAttribute("isAdmin", isAdmin);
    }

}
