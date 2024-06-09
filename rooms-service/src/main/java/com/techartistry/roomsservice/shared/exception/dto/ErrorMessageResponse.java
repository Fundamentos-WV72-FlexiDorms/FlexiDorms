package com.techartistry.roomsservice.shared.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private String details;
    private String uri;
    private Date timestamp;
}
