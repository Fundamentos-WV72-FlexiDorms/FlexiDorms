package com.techartistry.bookingsservice.bookings.infrastructure.repositories;

import com.techartistry.bookingsservice.bookings.domain.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentId(String studentId);
    List<Booking> findByLessorId(String lessorId);
}
