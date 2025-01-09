package ru.alex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthDto {

    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, message = "Password must contains at least 6 symbols")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
