package com.ashgrid.qrcodeapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @Column(nullable = false)
private String fullName;
    @Column(nullable = false, unique = true)
private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // Add role attribute
    public enum Role {
        SIMPLE_USER,
        ADMIN,
        SUPER_ADMIN
    }

}
