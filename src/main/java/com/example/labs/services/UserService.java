package com.example.labs.services;

import com.example.labs.models.User;
import com.example.labs.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username+" not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Получаем всех пользователей из базы данных
    }

    public void registerUser(String username, String password, String firstname, String lastname, String email, String phone, String confirmPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Кодируем пароль только здесь
        user.setConfirmPassword(passwordEncoder.encode(confirmPassword));
        user.setRole("ROLE_USER");
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRegistrationDate(LocalDate.now());
        userRepository.save(user);
    }
}
