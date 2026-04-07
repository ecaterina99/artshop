package com.server.ArtShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "This field can't be empty")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'USER'")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
