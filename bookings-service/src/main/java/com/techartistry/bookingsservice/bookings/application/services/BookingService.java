package com.techartistry.bookingsservice.bookings.application.services;

import com.techartistry.bookingsservice.bookings.application.clients.IRoomClient;
import com.techartistry.bookingsservice.bookings.application.clients.IUserClient;
import com.techartistry.bookingsservice.bookings.application.dto.request.RegisterBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.request.UpdateBookingRequestDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.LessorBookingResponseDto;
import com.techartistry.bookingsservice.bookings.application.dto.response.StudentBookingResponseDto;
import com.techartistry.bookingsservice.bookings.domain.entities.Booking;
import com.techartistry.bookingsservice.bookings.domain.entities.Room;
import com.techartistry.bookingsservice.bookings.domain.entities.User;
import com.techartistry.bookingsservice.bookings.domain.enums.EBookingStatus;
import com.techartistry.bookingsservice.bookings.domain.enums.EPaymentStatus;
import com.techartistry.bookingsservice.bookings.domain.events.BookingCreatedEvent;
import com.techartistry.bookingsservice.bookings.infrastructure.repositories.IBookingRepository;
import com.techartistry.bookingsservice.events.service.KafkaProducerService;
import com.techartistry.bookingsservice.shared.dto.ApiResponse;
import com.techartistry.bookingsservice.shared.exception.CustomException;
import com.techartistry.bookingsservice.shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class BookingService implements IBookingService {
    private final ModelMapper modelMapper;
    private final IBookingRepository bookingRepository;
    private final IRoomClient roomClient;
    private final IUserClient userClient;
    private final KafkaProducerService kafkaProducerService;

    public BookingService(ModelMapper modelMapper, IBookingRepository bookingRepository, IRoomClient roomClient, IUserClient userClient, KafkaProducerService kafkaProducerService) {
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
        this.roomClient = roomClient;
        this.userClient = userClient;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public ApiResponse<StudentBookingResponseDto> getBookingById(Long bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

        //setea los campos Transient de la entidad Booking
        var student = getUserById(booking.getStudentId());
        booking.setStudent(student);

        var room = getRoomById(booking.getRoomId());
        booking.setRoom(room);

        //convertir el objeto de tipo Booking (entity) a un objeto de tipo RegisterRoomResponseDto (dto)
        var studentBookingResponseDto = modelMapper.map(booking, StudentBookingResponseDto.class);
        return new ApiResponse<>("Ok", true, studentBookingResponseDto);
    }

    @Override
    public ApiResponse<Object> registerBooking(RegisterBookingRequestDto request) {
        var student = getUserById(request.getStudentId());
        var room = getRoomById(request.getRoomId());

        //se valida que el checkInDate sea menor a checkOutDate
        if (request.getCheckInDate().isAfter(request.getCheckOutDate())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error registering booking", "CheckInDate must be before CheckOutDate");
        }

        //se verifica si la habitación está disponible en el rango de fechas
        var overlappingBookings = bookingRepository.findOverlappingBookings(request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());

        //si hay reservas superpuestas, se envía un mensaje de error
        if (!overlappingBookings.isEmpty()) {
            //se obtiene la reserva que termina más tarde
            var nextBooking = bookingRepository.findNextAvailableBooking(request.getRoomId(), request.getCheckInDate()).getFirst();
            var message = String.format(
                "Room is not available in the specified time range. The room is occupied until %s.",
                nextBooking.getCheckOutDate().format(DateTimeFormatter.ofPattern("hh:mm a, MMM dd"))
            );

            throw new CustomException(HttpStatus.BAD_REQUEST, "Error registering booking", message);
        }

        //se calcula el total de la reserva (x horas)
        long totalHours = request.getCheckInDate().until(request.getCheckOutDate(), ChronoUnit.HOURS);
        var totalAmount = room.getPrice() * totalHours;

        var booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(EBookingStatus.PENDING);
        booking.setPaymentStatus(EPaymentStatus.PENDING);
        booking.setRoomId(room.getRoomId());
        booking.setStudentId(student.getId());
        booking.setLessorId(room.getLessor().getId());

        bookingRepository.save(booking);

        //se publica el evento de reserva creada (para el servicio de pagos)
        kafkaProducerService.publishBookingCreatedEvent(new BookingCreatedEvent(booking.getBookingId(), booking.getTotalAmount()));

        return new ApiResponse<>("Booking was successfully registered", true, null);
    }

    @Override
    public ApiResponse<List<StudentBookingResponseDto>> getAllBookingsByStudentId(String studentId) {
        var student = getUserById(studentId);
        var bookings = bookingRepository.findByStudentId(student.getId());

        //convertir la lista de objetos de tipo Booking (entity) a una lista de objetos de tipo StudentBookingResponseDto (dto)
        var roomResponseList = bookings.stream()
                .map(booking -> {
                    //asignar el room a la reserva desde la respuesta del servicio externo (rooms-service)
                    var room = getRoomById(booking.getRoomId());
                    booking.setRoom(room);

                    return modelMapper.map(booking, StudentBookingResponseDto.class);
                })
                .toList();

        return new ApiResponse<>("Ok", true, roomResponseList);
    }

    @Override
    public ApiResponse<List<LessorBookingResponseDto>> getAllBookingsByLessorId(String lessorId) {
        var lessor = getUserById(lessorId);
        var bookings = bookingRepository.findByLessorId(lessor.getId());

        var roomResponseList = bookings.stream()
                .map(booking -> {
                    var room = getRoomById(booking.getRoomId());
                    var student = getUserById(booking.getStudentId());
                    booking.setRoom(room);
                    booking.setStudent(student);

                    return modelMapper.map(booking, LessorBookingResponseDto.class);
                })
                .toList();

        return new ApiResponse<>("Ok", true, roomResponseList);
    }

    @Override
    public ApiResponse<Object> updateBooking(Long bookingId, UpdateBookingRequestDto request) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

        booking.setCheckInDate(request.checkInDate() != null ? request.checkInDate() : booking.getCheckInDate());
        booking.setCheckOutDate(request.checkOutDate() != null ? request.checkOutDate() : booking.getCheckOutDate());
        booking.setStatus(request.status() != null ? request.status() : booking.getStatus());
        booking.setPaymentStatus(request.paymentStatus() != null ? request.paymentStatus() : booking.getPaymentStatus());
        bookingRepository.save(booking);

        return new ApiResponse<>("Booking status was successfully updated", true, null);
    }

    /**
     * Obtiene el usuario por su id desde otro servicio (account-service)
     * @param userId El id del usuario
     * @return El usuario
     */
    private User getUserById(String userId) {
        var userRes = userClient.getUserById(userId);
        return Objects.requireNonNull(userRes.getBody()).getData();
    }

    /**
     * Obtiene la habitación por su id desde otro servicio (rooms-service)
     * @param roomId El id de la habitación
     * @return La habitación
     */
    private Room getRoomById(Long roomId) {
        var roomRes = roomClient.getRoomById(roomId);
        return Objects.requireNonNull(roomRes.getBody()).getData();
    }
}
