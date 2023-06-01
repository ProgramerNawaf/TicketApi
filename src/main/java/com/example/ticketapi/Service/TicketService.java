package com.example.ticketapi.Service;


import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.DTO.TicketDTO;
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
// Integer userId, String eventName, Integer ticketsNum
    public Double buyTickets(TicketDTO dto) {

        Event event = eventRepository.findEventByName(dto.getEventName());
        if ((event == null)) {
            throw new ApiException("Event not Found");
        }

        Company company = companyRepository.findCompanyByEventsContains(event);
        if (company == null) {
            throw new ApiException("Company not found");
        }

        MyUser myUser = myUserRepository.findMyUserById(dto.getUserId());
        if (myUser == null){
            throw new ApiException("User Not Found");
        }

        Double price = event.getPrice() * dto.getTicketNum();
        if (dto.getDiscountCode().equals("JavaBootcamp23")){
            price = price*( 1 - 0.10);
        }

        //check age
        if (event.getAgeLimit() > myUser.getAge()) {
            throw new ApiException("Sorry the age limit is " + event.getAgeLimit());
        }
        //check user balance
        if (price > myUser.getBalance()) {
            throw new ApiException("Balance not enough");
        }
        //check capacity
        if (event.getCapacity() <= dto.getTicketNum()) {
            throw new ApiException("Sorry no more tickets");
        }

        myUser.setBalance(myUser.getBalance() - price);
        event.setCapacity(event.getCapacity() - dto.getTicketNum());
        company.setRevenue(company.getRevenue() + price);

        for (int i = 0; i < dto.getTicketNum(); i++) {
            Ticket ticket = new Ticket();
            if (dto.getDiscountCode().equals("JavaBootcamp23")){
                ticket.setDiscountUsed(true);
            }
            ticket.setEvent(event);
            ticket.setUser(myUser);
            ticket.setEventName(event.getName());
            ticket.setDate(event.getDate());
            ticketRepository.save(ticket);
        }
        return price;
    }


    public Double cancelTicket(TicketDTO dto){
        Date date = new Date();
        MyUser user = myUserRepository.findMyUserById(dto.getUserId());
        if( user == null)
            throw new ApiException("user id invalid");
        Event event = eventRepository.findEventByName(dto.getEventName());
        if(event.getDate().before(date))
            throw new ApiException("Refund expired!");

        Company company = companyRepository.findCompanyById(event.getCompany().getId());
        List<Ticket> tickets = ticketRepository.findTicketsByUser(user);
        if (tickets.isEmpty()){
            throw new ApiException("You do not have any ticket");
        }
        Double price = 0.0;
        Double sum = 0.0;
        for(int i = 0 ; i<dto.getTicketNum(); i++) {
            price =  tickets.get(i).getEvent().getPrice();
            if (tickets.get(i).getDiscountUsed()){
                price = price*(1 - 0.10);
            }
            sum =price + sum;
            ticketRepository.delete(tickets.get(i));
        }

        company.setRevenue(company.getRevenue() - sum);
        user.setBalance(user.getBalance() + sum);
        event.setCapacity(event.getCapacity()+dto.getTicketNum());
        eventRepository.save(event);
        myUserRepository.save(user);
        companyRepository.save(company);
        return sum;
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

//    public List<Ticket> getTicketByDate(Integer userId,Date date){
//        MyUser myUser = myUserRepository.findMyUserById(userId);
//        if (myUser ==null){
//            throw new ApiException("user No");
//        }
//        List<Ticket> tickets1 = ticketRepository.findTicketsByDate(date, tickets);
//        if (tickets1.isEmpty()){
//            throw new ApiException("Cannot find ticket on this date");
//        }
//        return tickets;
//    }
}
