package com.example.ticketapi.Service;


import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;


    public void cancelTicket(Integer ticket_id){
        Ticket t = ticketRepository.findTicketById(ticket_id);
        if( t== null)
            throw new ApiException("ticket id invalid!");
        //Number of tickets this user has
        MyUser user = t.getUser();
        Integer userTickets= user.getTickets().size();
        //total price need to be refunded
        Double priceRefund = userTickets *  t.getEvent().getPrice();
        //company orgnizing this event
        Company company = t.getEvent().getCompany();
        company.setRevenue(company.getRevenue()-priceRefund);
        //user refund money
        user.setBalance(user.getBalance()+priceRefund);
    }
}
