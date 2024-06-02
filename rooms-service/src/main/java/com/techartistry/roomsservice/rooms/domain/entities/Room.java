package com.techartistry.roomsservice.rooms.domain.entities;

import com.techartistry.roomsservice.rooms.domain.enums.ERoomStatus;
import com.techartistry.roomsservice.rooms.infrastructure.mappers.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    private String address;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> amenities;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    private List<String> rules;

    @Column(nullable = false)
    private double price;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERoomStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //referencia a la tabla de usuarios (de Keycloak) -> un usuario puede tener muchas habitaciones (no es FK)
    @Column(nullable = false)
    private String lessorId;

    //campo solo para mostrar en la respuesta y la lógica de la aplicación, no se guarda en la bd
    @Transient
    private Lessor lessor;
}
