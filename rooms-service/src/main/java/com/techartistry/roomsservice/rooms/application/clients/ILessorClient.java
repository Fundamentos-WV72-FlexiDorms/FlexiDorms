package com.techartistry.roomsservice.rooms.application.clients;

import com.techartistry.roomsservice.rooms.domain.entities.Lessor;
import com.techartistry.roomsservice.shared.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface ILessorClient {
    @GetMapping("/api/user/id/{userId}")
    ResponseEntity<ApiResponse<Lessor>> getLessorById(@PathVariable String userId);
}
