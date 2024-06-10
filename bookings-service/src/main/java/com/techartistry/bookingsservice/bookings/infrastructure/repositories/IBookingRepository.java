package com.techartistry.bookingsservice.bookings.infrastructure.repositories;

import com.techartistry.bookingsservice.bookings.domain.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentId(String studentId);
    List<Booking> findByLessorId(String lessorId);

    /**
     * Busca reservas que se solapen con las fechas de entrada y salida de una reserva
     */
    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    List<Booking> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDateTime checkInDate,
            @Param("checkOutDate") LocalDateTime checkOutDate
    );

    /**
     * Busca la siguiente reserva disponible para una habitación
     */
    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND b.checkOutDate > :currentDateTime ORDER BY b.checkOutDate ASC")
    List<Booking> findNextAvailableBooking(@Param("roomId") Long roomId, @Param("currentDateTime") LocalDateTime currentDateTime);
}
