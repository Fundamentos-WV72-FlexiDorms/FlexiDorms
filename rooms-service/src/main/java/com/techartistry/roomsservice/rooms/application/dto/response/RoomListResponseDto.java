package com.techartistry.roomsservice.rooms.application.dto.response;

import com.techartistry.roomsservice.rooms.domain.enums.ERoomStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RoomListResponseDto {
    private Long roomId;
    private String name;
    private String address;
    private double price;
    private String imageUrl;
    private ERoomStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomListResponseDto() {}
}
