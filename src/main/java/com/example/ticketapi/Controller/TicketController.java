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
    public ResponseEntity buyTickets(@PathVariable Integer userId, @PathVariable String eventName, @PathVariable Integer ticketNum) {
        ticketService.buyTickets(userId, eventName, ticketNum);
        return ResponseEntity.status(200).body("Transaction done");
    }

    @GetMapping("/check-ticket/{idTicket}/{nameEvent}")
    public ResponseEntity checkValidTicket(@PathVariable Integer idTicket, @PathVariable String nameEvent) {
        ticketService.checkValidTicket(idTicket, nameEvent);
        return ResponseEntity.status(200).body("Valid ticket");

    }


}
