package com.example.ticketapi.Controller;


import com.example.ticketapi.ApiException.ApiException;
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
        if(eventService.getEvents().isEmpty())
            throw new ApiException("no events available");
        return ResponseEntity.status(200).body(eventService.getEvents());
    }
    @PostMapping("/add")
    public ResponseEntity addEvent(@Valid @RequestBody Event event ){
        System.out.println("hello");
        //@PathVariable Integer companyId
        eventService.addEvent(event);
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



}
