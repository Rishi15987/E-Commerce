package com.self.e_commerce.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "roles")
    private Set<UserInfo> users = new HashSet<>();
    public Role(String roleUser) {
        this.name=roleUser;
    }
}
