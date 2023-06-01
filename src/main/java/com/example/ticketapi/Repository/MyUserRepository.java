package com.example.ticketapi.Repository;

import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

    MyUser findMyUserById(Integer id);

    MyUser findMyUsersByTicketsContains(Ticket t);

    List<MyUser> findMyUsersByRoleContains(String role);

    
}
