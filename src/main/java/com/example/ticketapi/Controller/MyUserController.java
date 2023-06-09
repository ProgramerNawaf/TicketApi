package com.example.ticketapi.Controller;

import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Repository.MyUserRepository;
import com.example.ticketapi.Repository.TicketRepository;
import com.example.ticketapi.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/myuser")
@RequiredArgsConstructor
public class MyUserController {


    private final MyUserService myUserService;

    @GetMapping("/get-all-users/{idAdmin}")
    public ResponseEntity getALlMyUsers(@PathVariable Integer idAdmin) {
        List<MyUser> myUsers = myUserService.getALlMyUsers(idAdmin);
        return ResponseEntity.status(200).body(myUsers);
    }

    @PostMapping("/add-user")
    public ResponseEntity addMyUser(@Valid @RequestBody MyUser myUser) {
        String addedMyUser = myUserService.addMyUser(myUser);
        return ResponseEntity.status(200).body(addedMyUser);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity updateMyUser(@PathVariable Integer id, @Valid @RequestBody MyUser myUser) {
        myUserService.updateMyUser(id, myUser);
        return ResponseEntity.status(200).body("done update");
    }

    @DeleteMapping("/delete/{idForUser}")
    public ResponseEntity deleteMyUse(@PathVariable Integer idForUser) {
        myUserService.deleteMyUser(idForUser);
        return ResponseEntity.status(200).body("done delete");
    }

    @GetMapping("/get-by-role/{role}")
    public ResponseEntity getOnlyCustomers(@PathVariable String role) {
        List<MyUser> myUsers = myUserService.getByRole(role);
        return ResponseEntity.status(200).body(myUsers);

    }

    // get MyUser by id
    @GetMapping("/get-id/{userId}")
    public ResponseEntity getUserById(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(myUserService.getUserById(userId));
    }
    // get user tickets
    @GetMapping("/get-tickets/{userId}")
    public ResponseEntity getUserTickets(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(myUserService.getUserTickets(userId));
    }

}

