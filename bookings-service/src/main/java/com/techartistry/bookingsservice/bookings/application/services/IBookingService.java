package com.techartistry.bookingsservice.bookings.application.services;

import com.techartistry.bookingsservice.bookings.application.dto.request.RegisterBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.request.UpdateBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.LessorBookingResponseDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.StudentBookingResponseDto;
import com.techartistry.bookingsservice.shared.dto.ApiResponse;

import java.util.List;

public interface IBookingService {
    /**
     * Obtiene una reserva por su id
     * @param bookingId Id de la reserva
     */
    ApiResponse<StudentBookingResponseDto> getBookingById(Long bookingId);

    /**
     * Registra una reserva
     * @param request Datos de la reserva
     */
    ApiResponse<Object> registerBooking(RegisterBookingRequestDto request);

    /**
     * Obtiene todas las reservas de un estudiante (SOLO PARA ESTUDIANTES)
     * @param studentId Id del estudiante
     */
    ApiResponse<List<StudentBookingResponseDto>> getAllBookingsByStudentId(String studentId);

    /**
     * Obtiene todas las reservas de un arrendador (SOLO PARA ARRENDADORES)
     * @param lessorId Id del estudiante
     */
    ApiResponse<List<LessorBookingResponseDto>> getAllBookingsByLessorId(String lessorId);

    /**
     * Actualiza una reserva
     * @param bookingId Id de la reserva
     * @param request Nuevos datos de la reserva
     */
    ApiResponse<Object> updateBooking(Long bookingId, UpdateBookingRequestDto request);
}
