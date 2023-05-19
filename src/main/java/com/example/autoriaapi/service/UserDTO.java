package com.example.autoriaapi.service;

import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public Set<Role> setRoles(Set<String> roles) {
        this.roles = roles;
        return null;
    }

    public Set<Role> setRoles(ERole roleUpdateSeller) {
        return null;
    }

    //private String username;


}