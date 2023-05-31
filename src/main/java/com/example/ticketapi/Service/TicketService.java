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
import org.apache.catalina.User;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;



@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MyUserRepository myUserRepository;
    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;

    public void buyTickets(Integer userId, String eventName, Integer ticketsNum) {

        Event event = eventRepository.findEventByName(eventName);
        if ((event == null)) {
            throw new ApiException("Event not Found");
        }

        Company company = companyRepository.findCompanyByEventsContains(event);
        if (company == null) {
            throw new ApiException("Company not found");
        }

        MyUser myUser = myUserRepository.findMyUserById(userId);
        if (myUser == null){
            throw new ApiException("User Not Found");
        }

        Double price = event.getPrice() * ticketsNum;

        //check age
        if (event.getAgeLimit() > myUser.getAge()) {
            throw new ApiException("Sorry the age limit is " + event.getAgeLimit());
        }
        //check user balance
        if (price > myUser.getBalance()) {
            throw new ApiException("Balance not enough");
        }
        //check capacity
        if (event.getCapacity() <= ticketsNum) {
            throw new ApiException("Sorry no more tickets");
        }

        myUser.setBalance(myUser.getBalance() - price);
        event.setCapacity(event.getCapacity() - ticketsNum);
        company.setRevenue(company.getRevenue() + price);

        for (int i = 0; i < ticketsNum; i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setUser(myUser);
            ticketRepository.save(ticket);
        }

    }


    public void cancelTicket(Integer userId , String eventName , Integer ticketNumber){
        Date date = new Date();
        MyUser user = myUserRepository.findMyUserById(userId);
        if( user == null)
            throw new ApiException("ticket id invalid");
        Event event = eventRepository.findEventByName(eventName);
        if(event.getDate().before(date))
            throw new ApiException("Refund expired!");

        Company company = companyRepository.findCompanyById(event.getCompany().getId());
        List<Ticket> tickets = ticketRepository.findTicketsByUser(user);
        Double price = 0.0;
        for(int i = 0 ; i<ticketNumber; i++) {
            price = price + tickets.get(i).getEvent().getPrice();
            ticketRepository.delete(tickets.get(i));
        }

        company.setRevenue(company.getRevenue() - price);
        user.setBalance(user.getBalance() + price);
        event.setCapacity(event.getCapacity()+ticketNumber);
        eventRepository.save(event);
        myUserRepository.save(user);
        companyRepository.save(company);
    }

    public void checkValidTicket(Integer idTicket, String nameEvent) {
        Date date = new Date();
        Ticket ticket = ticketRepository.findTicketById(idTicket);
        Event event = eventRepository.findEventByName(nameEvent);
        if (ticket.getEvent().getDate().before(date)) {
            throw new ApiException("Ticket expired");
        }
        if (ticket.getEvent() != event) {
            throw new ApiException("Ticket not for this event!");
        }

    }
}
