package ru.praktikum;

public class RegisterUser {
    private String email;
    private String password;
    private String name;

    public RegisterUser() {}

    public RegisterUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public RegisterUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterUser setName(String name) {
        this.name = name;
        return this;
    }
}
