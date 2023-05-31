package com.example.ticketapi.Repository;

import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findTicketsByEvent(Event event);

    Ticket findTicketById(Integer id);
}
