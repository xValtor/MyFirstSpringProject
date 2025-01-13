package com.example.labs.configs;

import com.example.labs.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/products", "/css/**", "/js/**", "/images/**","/products/item/{id}").permitAll() // Доступ без авторизации
                        .requestMatchers("/products/new","products/adminPanel","/products/edit/{id}").hasRole("ADMIN")                // Доступ для роли ADMIN
                        .anyRequest().authenticated()                             // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login")                                     // URL страницы логина
                        .defaultSuccessUrl("/products", true)                           // Перенаправление после успешного входа
                        .failureUrl("/login?error=true")                        // Перенаправление в случае ошибки
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                                   // URL выхода
                        .logoutSuccessUrl("/products")
                        .invalidateHttpSession(true) // Уничтожить сессию
                        .deleteCookies("JSESSIONID") // Удалить куки сессии// Перенаправление после выхода
                        .permitAll()
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используйте BCrypt для хэширования
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Используется UserService
        authProvider.setPasswordEncoder(passwordEncoder()); // Используется BCryptPasswordEncoder
        return authProvider;
    }


}
