package com.example.ticketapi.Controller;


import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.DTO.TicketDTO;
import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Service.CompanyService;
import com.example.ticketapi.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    @GetMapping("/get")
    public ResponseEntity getEvents(){
        return ResponseEntity.status(200).body(eventService.getEvents());
    }
    @PostMapping("/add/{companyId}")
    public ResponseEntity addEvent(@Valid @RequestBody Event event , @PathVariable  Integer companyId){
        eventService.addEvent(event,companyId);
        return ResponseEntity.status(200).body("Event added");
    }

    @PutMapping("/update/{companyId}/{eventId}")
    public ResponseEntity updateEvent(@Valid @RequestBody Event event , @PathVariable("companyId") Integer companyId, @PathVariable("eventId") Integer eventId){
        eventService.updateEvent(event,eventId,companyId);
        return ResponseEntity.status(200).body("Event updated");
    }

    @DeleteMapping ("/delete/{companyId}/{eventId}")
    public ResponseEntity deleteEvent(@PathVariable("companyId") Integer companyId, @PathVariable("eventId") Integer eventId){
        eventService.deleteEvent(eventId,companyId);
        return ResponseEntity.status(200).body("Event deleted");
    }

    @GetMapping("/tickets_sold/{eventName}")
    public ResponseEntity ticketsSold(@PathVariable("eventName") String eventName ){
       Integer sold = eventService.ticketsSold(eventName);
        return ResponseEntity.status(200).body(sold);
    }

    @GetMapping("/tickets-left/{eventName}")
    public ResponseEntity ticketsLeft(@PathVariable("eventName") String eventName ){
        Integer left = eventService.ticketsLeft(eventName);
        return ResponseEntity.status(200).body(left);
    }

    @PutMapping("/postpone/{eventName}")
    public ResponseEntity postponeEvent(@PathVariable("eventName") String eventName, @Valid @RequestBody TicketDTO dto){
        eventService.postponeEvent(eventName,dto);
        return ResponseEntity.status(200).body("Event PostPone");
    }


}
