package com.techartistry.accountservice.user.domain.entities;

import com.techartistry.accountservice.user.domain.enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    //se tomará como un String los valores de este enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole name;

    public Role() {}
}
