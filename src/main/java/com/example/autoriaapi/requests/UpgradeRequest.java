package com.example.autoriaapi.requests;

import java.util.Set;

public class UpgradeRequest {
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}