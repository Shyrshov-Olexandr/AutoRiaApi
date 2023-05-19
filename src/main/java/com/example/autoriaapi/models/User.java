package com.example.autoriaapi.models;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CarUser> cars;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Set<String> roles) {
    }

    public void addCar(CarUser carUser) {
        carUser.setUser(this);
        cars.add(carUser);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<CarUser> getCars() {
        return cars;
    }

    public void setCars(List<CarUser> cars) {
        this.cars = cars;
    }

    public void setRoles(ERole roleUpSeller) {
    }

    public int getViewCountForDay() {
        return cars.stream()
                .filter(car -> car.getLastViewTime().isAfter(LocalDateTime.now().minusDays(1)))
                .mapToInt(CarUser::getView)
                .sum();
    }

    public int getViewCountForWeek() {
        return cars.stream()
                .filter(car -> car.getLastViewTime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .mapToInt(CarUser::getView)
                .sum();
    }

    public int getViewCountForMonth() {
        return cars.stream()
                .filter(car -> car.getLastViewTime().isAfter(LocalDateTime.now().minusMonths(1)))
                .mapToInt(CarUser::getView)
                .sum();
    }
}