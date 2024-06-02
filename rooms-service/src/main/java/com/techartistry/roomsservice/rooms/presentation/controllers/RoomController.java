package com.techartistry.roomsservice.rooms.presentation.controllers;

import com.techartistry.roomsservice.rooms.application.dto.request.RegisterRoomRequestDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomListResponseDto;
import com.techartistry.roomsservice.rooms.application.dto.response.RoomResponseDto;
import com.techartistry.roomsservice.rooms.application.services.IRoomService;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rooms")
@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Register a room")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerRoom(@Valid @RequestBody RegisterRoomRequestDto request){
        var res = roomService.registerRoom(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a room by id")
    @GetMapping("/id/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> getRoomById(@PathVariable Long roomId){
        var res = roomService.getRoomById(roomId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get rooms by lessorId")
    @GetMapping("/list/lessorId/{lessorId}")
    public ResponseEntity<ApiResponse<List<RoomResponseDto>>> getRoomsByLessorId(@PathVariable String lessorId){
        var res = roomService.getRoomsByLessorId(lessorId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get free rooms")
    @GetMapping("/free")
    public ResponseEntity<ApiResponse<Page<RoomListResponseDto>>> listRooms(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean asc
    ) {
        var res = roomService.listRooms(pageNumber, pageSize, sortBy, asc);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
