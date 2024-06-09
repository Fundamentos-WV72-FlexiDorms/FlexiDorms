package com.techartistry.roomsservice.rooms.application.services;

import com.techartistry.roomsservice.rooms.application.clients.ILessorClient;
import com.techartistry.roomsservice.rooms.application.dto.request.RegisterRoomRequestDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomListResponseDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomResponseDto;
import com.techartistry.roomsservice.rooms.domain.entities.Lessor;
import com.techartistry.roomsservice.rooms.domain.entities.Room;
import com.techartistry.roomsservice.rooms.infrastructure.repositories.IRoomRepository;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import com.techartistry.roomsservice.shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoomService implements IRoomService {
    private final ModelMapper modelMapper;
    private final IRoomRepository roomRepository;
    private final ILessorClient lessorClient;

    public RoomService(IRoomRepository roomRepository, ModelMapper modelMapper, ILessorClient lessorClient) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.lessorClient = lessorClient;
    }

    @Override
    public ApiResponse<Object> registerRoom(RegisterRoomRequestDto request) {
        var lessor = getLessorById(request.getLessorId());

        var room = new Room();
        room.setName(request.getName());
        room.setAddress(request.getAddress());
        room.setAmenities(request.getAmenities());
        room.setRules(request.getRules());
        room.setPrice(request.getPrice());
        room.setImageUrl(request.getImageUrl());
        room.setLessorId(lessor.getId());

        roomRepository.save(room);

        return new ApiResponse<>("Room was successfully registered", true, null);
    }

    @Override
    public ApiResponse<RoomResponseDto> getRoomById(Long roomId) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        var lessor = getLessorById(room.getLessorId());
        room.setLessor(lessor);

        //convertir el objeto de tipo Room (entity) a un objeto de tipo RegisterRoomResponseDto (dto)
        var roomResponseDto = modelMapper.map(room, RoomResponseDto.class);
        return new ApiResponse<>("Ok", true, roomResponseDto);
    }

    @Override
    public ApiResponse<List<RoomResponseDto>> getRoomsByLessorId(String lessorId) {
        var lessor = getLessorById(lessorId);
        var rooms = roomRepository.findByLessorId(lessorId);

        //convertir la lista de objetos de tipo Room (entity) a una lista de objetos de tipo RegisterRoomResponseDto (dto)
        var roomResponseList = rooms.stream()
                .map(room -> {
                    //asignar el lessor a la habitación desde la respuesta del servicio externo (account-service)
                    room.setLessor(lessor);
                    return modelMapper.map(room, RoomResponseDto.class);
                })
                .toList();

        return new ApiResponse<>("Ok", true, roomResponseList);
    }

    @Override
    public ApiResponse<Page<RoomListResponseDto>> listRooms(
            int pageNumber,
            int pageSize,
            String sortBy,
            boolean asc
    ) {
        //configurar la paginación
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                (asc ? Sort.by(sortBy) : Sort.by(sortBy).descending())
        );

        var rooms = roomRepository.findAll(pageable);

        //mapear Page<Room> a Page<RoomListResponseDto>
        var roomListResponse = rooms.map(room -> modelMapper.map(room, RoomListResponseDto.class));

        return new ApiResponse<>("Ok", true, roomListResponse);
    }

    /**
     * Obtiene el arrendador por su id desde otro servicio (account-service)
     * @param lessorId El id del arrendador
     * @return El arrendador
     */
    private Lessor getLessorById(String lessorId) {
        var lessorRes = lessorClient.getLessorById(lessorId);
        return Objects.requireNonNull(lessorRes.getBody()).getData();
    }
}
