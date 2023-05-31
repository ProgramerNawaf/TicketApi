package com.example.ticketapi.Controller;


import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/buy/{userId}/{eventName}/{ticketNum}")
    public ResponseEntity buyTickets(@PathVariable Integer userId, @PathVariable String eventName, @PathVariable Integer ticketNum){
        ticketService.buyTickets(userId, eventName, ticketNum);
        return ResponseEntity.status(200).body("Transaction done");
    }
        @DeleteMapping("/delete/{userId}/{eventName}/{ticketNum}")
    public ResponseEntity cancelTicket(@PathVariable Integer userId, @PathVariable String eventName, @PathVariable Integer ticketNum){
        ticketService.cancelTicket(userId, eventName, ticketNum);
        return ResponseEntity.status(200).body("cancelation complete");
    }
}
