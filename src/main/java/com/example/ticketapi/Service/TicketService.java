package com.example.ticketapi.Service;


import com.example.ticketapi.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
}
