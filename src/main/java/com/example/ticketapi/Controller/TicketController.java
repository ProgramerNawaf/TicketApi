package com.example.ticketapi.Controller;


import com.example.ticketapi.Service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
}
