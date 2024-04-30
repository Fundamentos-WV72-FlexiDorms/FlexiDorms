package com.techartistry.accountservice.user.domain.entities;

import com.techartistry.accountservice.user.domain.enums.EGender;
import com.techartistry.accountservice.security.domain.model.entities.Token;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
//se creará una sola tabla para todas las clases que hereden de esta
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 9, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 8, unique = true)
    private String dni;

    @Column(nullable = false)
    private LocalDate birthDate;

    //se tomará como un String los valores de este enum
    @Enumerated(EnumType.STRING)
    private EGender gender;

    private String profilePicture;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * -Info: MUCHOS "usuarios" pueden tener MUCHOS "roles"
     * -JoinTable: la tabla intermediaria que se creará
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * UN USER puede tener MUCHOS Tokens
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Token> tokens = new HashSet<>();

    public User() {}
}
