package com.example.autoriaapi.requests;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignUpRequest {
    @NotBlank
    private String username;
    @Email
    private String email;
    private Set<String> roles;
    @NotBlank

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}