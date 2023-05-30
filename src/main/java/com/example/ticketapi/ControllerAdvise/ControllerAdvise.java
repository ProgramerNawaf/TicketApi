package com.example.ticketapi.ControllerAdvise;


import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.ApiResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiException(ApiException e) {
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


}
