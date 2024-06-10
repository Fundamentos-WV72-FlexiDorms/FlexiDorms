package com.techartistry.bookingsservice.bookings.domain.entities;

import com.techartistry.bookingsservice.bookings.domain.enums.EBookingStatus;
import com.techartistry.bookingsservice.bookings.domain.enums.EPaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EBookingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private EPaymentStatus paymentStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //RELACIONES (NO SON FKs), los campos con @Transient no se guardan en la bd (son solo informativos)
    //una habitación puede tener muchas reservas
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    @Transient
    private Room room;

    //un estudiante puede tener muchas reservas
    @Column(name = "student_id", nullable = false)
    private String studentId;
    @Transient
    private User student;

    @Column(name = "lessor_id", nullable = false)
    private String lessorId;
    @Transient
    private User lessor;
}
