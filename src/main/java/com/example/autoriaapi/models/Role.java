package com.example.autoriaapi.models;



import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
@Data
@Entity
@Table(name = "roles")
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    public static Role valueOf(String key) {
        return Role.valueOf(key);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }
}