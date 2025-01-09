package ru.alex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDto {

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @Min(value = 0, message = "Age can't be less then 0")
    private int age;

    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, message = "Password must contains at least 6 symbols")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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
