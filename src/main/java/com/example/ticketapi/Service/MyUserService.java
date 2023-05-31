package com.example.ticketapi.Service;

import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new ApiException("user can't change role");
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
}
