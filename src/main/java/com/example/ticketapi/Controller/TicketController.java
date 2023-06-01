package com.example.ticketapi.Controller;


import com.example.ticketapi.DTO.TicketDTO;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
/*
{userId}/{eventName}/{ticketNum}
@PathVariable Integer userId, @PathVariable String eventName, @PathVariable Integer ticketNum
 */
    @PostMapping("/buy")
    public ResponseEntity buyTickets(@Valid @RequestBody TicketDTO dto) {
      Double  price = ticketService.buyTickets(dto);
        return ResponseEntity.status(200).body("Transaction done total price is " + price);
    }

    @DeleteMapping("/delete")
    public ResponseEntity cancelTicket(@Valid @RequestBody TicketDTO dto){
       Double refund = ticketService.cancelTicket(dto);
        return ResponseEntity.status(200).body("cancelation complete you total refund is " + refund);
    }


    @GetMapping("/check-ticket/{idTicket}/{nameEvent}")
    public ResponseEntity checkValidTicket(@PathVariable Integer idTicket, @PathVariable String nameEvent) {
        ticketService.checkValidTicket(idTicket, nameEvent);
        return ResponseEntity.status(200).body("Valid ticket");

    }

//    @GetMapping("/get-tickets-date/{userId}")
//    public ResponseEntity getTicketsByDate(@PathVariable Integer userId, @Valid @RequestBody TicketDTO dto){
//        return ResponseEntity.status(200).body(ticketService.getTicketByDate(userId, dto.getDate()));
//
//    }



}
