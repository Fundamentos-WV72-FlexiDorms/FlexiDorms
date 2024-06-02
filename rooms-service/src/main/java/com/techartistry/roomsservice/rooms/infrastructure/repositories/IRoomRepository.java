package com.techartistry.roomsservice.rooms.infrastructure.repositories;

import com.techartistry.roomsservice.rooms.domain.entities.Room;
import com.techartistry.roomsservice.rooms.domain.enums.ERoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    /**
     * Obtiene habitaciones con el status dado de forma paginada
     * @param status El status de la habitación
     * @return Lista de habitaciones
     */
    Page<Room> findByStatus(ERoomStatus status, Pageable pageable);

    /**
     * Obtiene las habitaciones del arrendador
     * @param lessorId Id del arrendador
     * @return Lista de sus habitaciones
     */
    List<Room> findByLessorId(String lessorId);
}
