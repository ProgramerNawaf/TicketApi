package com.example.ticketapi.Repository;

import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Ticket findTicketById(Integer id);

}
