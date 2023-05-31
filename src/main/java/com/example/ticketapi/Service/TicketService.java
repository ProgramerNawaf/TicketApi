package com.example.ticketapi.Service;


import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.EventRepository;
import com.example.ticketapi.Repository.TicketRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public String checkValidTicket(Integer idTicket, String nameEvent) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String message;
        Ticket ticket = ticketRepository.findTicketById(idTicket);
        Event event = eventRepository.findEventByName(nameEvent);
        System.out.println(ticket.getEvent().getDate());
        if (ticket.getEvent().getId() == event.getId()) {
            return message = "Valid ticket";
        }
        return message = "Invalid ticket";
    }

}
