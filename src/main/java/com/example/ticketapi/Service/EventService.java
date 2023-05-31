package com.example.ticketapi.Service;

import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.CompanyRepository;
import com.example.ticketapi.Repository.EventRepository;
import com.example.ticketapi.Repository.MyUserRepository;
import com.example.ticketapi.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MyUserRepository myUserRepository;
    private final TicketRepository ticketRepository;
    private final CompanyRepository companyRepository;

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    //user id to check if he is admin add otherways no
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public void addEvent(Event event, Integer companyId) {
        System.out.println(event.getDate());
        Company company = companyRepository.findCompanyById(companyId);
        if (company == null)
            throw new ApiException("company with this Id dosent exist!");

//        company.getEvents().add(event);
        event.setCompany(company);
        eventRepository.save(event);
        companyRepository.save(company);
    }



    //        e.getEventDate();
//        ZonedDateTime eventDate = null;
//        System.out.println(eventDate);
//        eventDate = ZonedDateTime.parse(e.getEventDate().toString(), formatter);
//        System.out.println(eventDate);
//        if(companyRepository.findCompanyById(companyId) == null)
//           throw new ApiException("company with this Id dosent exist!");


    public void updateEvent(Event e, Integer event_Id, Integer companyId) {
        if (companyRepository.findCompanyById(companyId) == null)
            throw new ApiException("company with this Id dosent exist!");

        Event oldEvent = eventRepository.findEventById(event_Id);
        if (oldEvent == null)
            throw new ApiException("event with this Id dosent exist!");
//        oldEvent.setEventDate(e.getEventDate());
        oldEvent.setName(e.getName());
        oldEvent.setCategory(e.getCategory());
        oldEvent.setCapacity(e.getCapacity());
        oldEvent.setAgeLimit(e.getAgeLimit());
        oldEvent.setPrice(e.getPrice());
        eventRepository.save(oldEvent);
    }

    //    Hello
    public void deleteEvent(Integer event_id, Integer company_id) {
        Date date = new Date();
        Company company = companyRepository.findCompanyById(company_id);
        MyUser user = null;
        Event event = eventRepository.findEventById(event_id);
        List<Ticket> tickets = ticketRepository.findTicketsByEvent(event);
        //
        if (companyRepository.findCompanyById(company_id) == null)
            throw new ApiException("no company with this Id!");
        //
        if (event == null)
            throw new ApiException("Event Not Found");

        if(event.getDate().after(date)) {
            //we will use these to deduct from company and refund money to customer
            //when you delete an event you need to delete all tickets and refund all the customers for ticket price
            for (int i = 0; i < tickets.size(); i++) {
                //deduct the money from company

                company.setRevenue(company.getRevenue() - tickets.get(i).getEvent().getPrice());
                //refund the money to customers
                user = myUserRepository.findMyUsersByTicketsContains(tickets.get(i));
                user.setBalance(user.getBalance() + tickets.get(i).getEvent().getPrice());
                tickets.get(i).setUser(null);
                tickets.get(i).setEvent(null);
                ticketRepository.delete(tickets.get(i));
            }
            eventRepository.delete(event);
        }else{

            for (int i = 0; i < tickets.size(); i++) {
                tickets.get(i).setUser(null);
                tickets.get(i).setEvent(null);
                ticketRepository.delete(tickets.get(i));
            }
            eventRepository.delete(event);
        }
    }


}
