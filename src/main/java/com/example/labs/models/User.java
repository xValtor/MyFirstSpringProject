package com.example.labs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @Transient // Указывает, что это поле не нужно сохранять в базу данных
    private String confirmPassword;

    @NotEmpty()
    private String role="ROLE_USER";

    private String firstName;
    private String lastName;
    private String phone;

    private LocalDate registrationDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username is required") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotEmpty() String getRole() {
        return role;
    }

    public void setRole(@NotEmpty() String role) {
        this.role = role;
    }


    public @Email(message = "Email should be valid") @NotEmpty(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") @NotEmpty(message = "Email is required") String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
