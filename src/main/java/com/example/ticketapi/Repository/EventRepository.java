package com.example.ticketapi.Repository;

import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

    Event findEventById(Integer id);
//    List<Ticket> findEventByTicketsContains(Event e);
    Event findEventByName(String name);
    List <Event> findEventsByCompany(Company company);

    List<Event> findAllByDate(Date date);
}
