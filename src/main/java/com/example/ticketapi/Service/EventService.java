package com.example.ticketapi.Service;

import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.EventRepository;
import com.example.ticketapi.Repository.MyUserRepository;
import com.example.ticketapi.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MyUserRepository myUserRepository;
    private final TicketRepository ticketRepository;

    public List<Event> getEvents(){
        return eventRepository.findAll();
    }
    //user id to check if he is admin add otherways no
    public void addEvent(Event e , Integer user_id){
        if(!(myUserRepository.findMyUserById(user_id).getRole().equalsIgnoreCase("admin")))
            return;
        eventRepository.save(e);
    }

    public void addEvent(Event e , Integer event_Id ,Integer user_id){
        if(!(myUserRepository.findMyUserById(user_id).getRole().equalsIgnoreCase("admin")))
            return;
        Event oldEvent =eventRepository.findEventById(event_Id);
        if(oldEvent == null)
            return;
        oldEvent.setEventDate(e.getEventDate());
        oldEvent.setName(e.getName());
        oldEvent.setCategory(e.getCategory());
        oldEvent.setCapacity(e.getCapacity());
        oldEvent.setAgeLimit(e.getAgeLimit());
        oldEvent.setPrice(e.getPrice());
        eventRepository.save(oldEvent);
    }

    public void deleteEvent(Integer event_id){
        Event event =eventRepository.findEventById(event_id);
        //we will use these to deduct from company and refund money to customer
        Company company = null;
        MyUser user = null;

        if(event == null)
            return;
        //when you delete an event you need to delete all tickets and refund all the customers for ticket price
        List<Ticket> tickets = eventRepository.findEventByTicketsContains(event);
        for(int i = 0 ; i < tickets.size() ; i++) {
            //deduct the money from company
            company = eventRepository.findEventById(event_id).getCompany();
            company.setRevenue(company.getRevenue()-tickets.get(i).getEvent().getPrice());
            //refund the money to customers
            user = myUserRepository.findMyUsersByTicketsContains(tickets.get(i));
            user.setBalance(user.getBalance() + tickets.get(i).getEvent().getPrice());
        }
        eventRepository.delete(event);
    }


}
