package com.example.ticketapi.Service;


import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.EventRepository;
import com.example.ticketapi.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public String checkValidTicket(Integer idTicket, String name) {
        String message;
        Ticket ticket = ticketRepository.findTicketById(idTicket);
        Event event = eventRepository.findEventByName(name);
        if (ticket.getEvent().getId() == event.getId()) {
            return message = "Valid ticket";
        }
        return message = "Invalid ticket";

    }

}
