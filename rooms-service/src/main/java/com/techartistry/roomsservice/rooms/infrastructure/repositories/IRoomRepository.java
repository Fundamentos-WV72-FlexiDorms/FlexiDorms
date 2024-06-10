package com.techartistry.roomsservice.rooms.infrastructure.repositories;

import com.techartistry.roomsservice.rooms.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    /**
     * Obtiene las habitaciones del arrendador
     * @param lessorId Id del arrendador
     * @return Lista de sus habitaciones
     */
    List<Room> findByLessorId(String lessorId);
}
