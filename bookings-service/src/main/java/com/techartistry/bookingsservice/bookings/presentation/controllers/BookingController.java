package com.techartistry.bookingsservice.bookings.presentation.controllers;

import com.techartistry.bookingsservice.bookings.application.dto.request.RegisterBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.request.UpdateBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.LessorBookingResponseDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.StudentBookingResponseDto;
import com.techartistry.bookingsservice.bookings.application.services.IBookingService;
import com.techartistry.bookingsservice.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rooms")
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get a booking by id")
    @GetMapping("/id/{bookingId}")
    public ResponseEntity<ApiResponse<StudentBookingResponseDto>> geBookingById(@PathVariable Long bookingId) {
        var res = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Register a booking")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerBooking(@Valid @RequestBody RegisterBookingRequestDto request) {
        var res = bookingService.registerBooking(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all bookings by studentId (JUST FOR STUDENTS)")
    @GetMapping("/list/studentId/{studentId}")
    public ResponseEntity<ApiResponse<List<StudentBookingResponseDto>>> getAllBookingsByStudentId(@PathVariable String studentId) {
        var res = bookingService.getAllBookingsByStudentId(studentId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all bookings by lessorId (JUST FOR LESSORS)")
    @GetMapping("/list/lessorId/{lessorId}")
    public ResponseEntity<ApiResponse<List<LessorBookingResponseDto>>> getAllBookingsByLessorId(@PathVariable String lessorId) {
        var res = bookingService.getAllBookingsByLessorId(lessorId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Update a booking")
    @PutMapping("/update/{bookingId}")
    public ResponseEntity<ApiResponse<Object>> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingRequestDto request) {
        var res = bookingService.updateBooking(bookingId, request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
