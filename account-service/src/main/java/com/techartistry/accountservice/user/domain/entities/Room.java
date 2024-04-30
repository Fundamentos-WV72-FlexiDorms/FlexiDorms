package com.techartistry.accountservice.user.domain.entities;

import com.techartistry.accountservice.user.domain.enums.ERoomStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ERoomStatus status;

    private String imageUrl;

    //MUCHAS "habitaciones" van a estar en UN "arrendador"
    @ManyToOne
    @JoinColumn(name = "lessor_id", nullable = false)
    private Lessor lessor;
}