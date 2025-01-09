package ru.alex.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Min(value = 0, message = "Age can not be less then 0")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Size(min = 6, message = "Password must contains at least 6 symbols")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}

    public Person(String name, int age, String email, List<Book> books) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
