package com.ashgrid.qrcodeapp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String phoneId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Attendance> attendances;

    @Enumerated(EnumType.STRING)
    private Role role; // Add role attribute
    public enum Role {
        SIMPLE_USER,
        ADMIN,
        SUPER_ADMIN
    }

}
