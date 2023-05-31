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


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MyUserRepository myUserRepository;
    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;

    public void buyTickets(Integer userId, String eventName, Integer ticketsNum){
        Event event = eventRepository.findEventByName(eventName);
        Company company = companyRepository.findCompanyByEventsContains(event);
        MyUser myUser = myUserRepository.findMyUserById(userId);
        Double price = event.getPrice()*ticketsNum;

        if (company == null){
            throw new ApiException("Company not found");
        }
        if ((event == null)){
            throw new ApiException("Event not Found");
        }
        //check age
        if (event.getAgeLimit() > myUser.getAge()){
            throw new ApiException("Sorry the age limit is " + event.getAgeLimit());
        }
        //check user balance
        if (price > myUser.getBalance()){
            throw new ApiException("Balance not enough");
        }
        //check capacity
        if (event.getCapacity() <= ticketsNum){
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
}
