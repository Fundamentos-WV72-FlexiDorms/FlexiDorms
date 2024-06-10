package com.techartistry.roomsservice.rooms.application.services;

import com.techartistry.roomsservice.rooms.application.dto.request.RegisterRoomRequestDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomListResponseDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomResponseDto;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoomService {
    /**
     * Registra una habitación
     * @param request La habitación a registrar
     */
    ApiResponse<Object> registerRoom(RegisterRoomRequestDto request);

    /**
     * Obtiene una habitación por su id
     * @param roomId El id de la habitación
     * @return La habitación
     */
    ApiResponse<RoomResponseDto> getRoomById(Long roomId);

    /**
     * Obtiene las habitaciones de un arrendador
     * @param lessorId El id del arrendador
     * @return Lista de habitaciones
     */
    ApiResponse<List<RoomResponseDto>> getRoomsByLessorId(String lessorId);

    /**
     * Obtiene las habitaciones que están disponibles del más reciente al más antiguo
     * @param pageNumber Número de página (0 para la primera página)
     * @param pageSize Tamaño de la página (10 por defecto)
     * @param sortBy Campo por el cual ordenar (por defecto es la fecha de creación)
     * @param asc Si la ordenación es ascendente (por defecto es descendente)
     * @return Lista de habitaciones
     */
    ApiResponse<Page<RoomListResponseDto>> listRooms(
            int pageNumber,
            int pageSize,
            String sortBy,
            boolean asc
    );
}
