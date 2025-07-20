package ru.praktikum.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUser that = (RegisterUser) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name);
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
