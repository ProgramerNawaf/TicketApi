package com.example.ticketapi.Service;

import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Model.Ticket;
import com.example.ticketapi.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    //get all customer
    public List<MyUser> getALlMyUsers(Integer idUser) {
        MyUser myUser = myUserRepository.findMyUserById(idUser);
        if (myUser.getRole().equalsIgnoreCase("customer")) {
            throw new ApiException("do not has access for view all users");
        }
        List<MyUser> myUsers = myUserRepository.findAll();
        return myUsers;
    }

    public String addMyUser(MyUser myUser) {
        String message;
        if (myUser.getBalance() < 0) {
            throw new ApiException("balance must be 0 or more");
        }
        if (myUser.getRole().equalsIgnoreCase("customer")) {
            message = "customer add";
        } else {
            message = "admin add";
        }
        myUserRepository.save(myUser);
        return message;
    }

    public void updateMyUser(Integer id, MyUser myUser) {
        MyUser oldMyUser = myUserRepository.findMyUserById(id);
        if (oldMyUser == null) {
            throw new ApiException("do not have any user by this id");
        }
        oldMyUser.setAge(myUser.getAge());
        oldMyUser.setName(myUser.getName());
        oldMyUser.setBalance(myUser.getBalance());
        oldMyUser.setGender(myUser.getGender());
        oldMyUser.setEmail(myUser.getEmail());
        if (oldMyUser.getRole().equalsIgnoreCase("customer")) {
            throw new ApiException("customer can't change role");
        }
        oldMyUser.setRole(myUser.getRole());
        myUserRepository.save(oldMyUser);

    }

    public void deleteMyUser(Integer idForUser) {
        MyUser user = myUserRepository.findMyUserById(idForUser);
        if (user == null) {
            throw new ApiException("do not have any user by this id");
        }
        myUserRepository.delete(user);


    }

    public List<MyUser> getByRole(String role) {
        List<MyUser> myUsers = myUserRepository.findMyUsersByRoleContains(role);
        if (myUsers.isEmpty()) {
            throw new ApiException("do not have any " + role);
        }
        return myUsers;
    }


    // get MyUser by id
    public MyUser getUserById(Integer userId){
        MyUser myUser =myUserRepository.findMyUserById(userId);
        if (myUser == null){
            throw new ApiException("User Not Found");
        }
        return myUser;
    }

    // get user tickets
    public Set<Ticket> getUserTickets(Integer userId){
        MyUser myUser =myUserRepository.findMyUserById(userId);
        if (myUser == null){
            throw new ApiException("User Not Found");
        }
        return myUser.getTickets();
    }
}

