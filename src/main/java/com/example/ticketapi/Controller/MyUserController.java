package com.example.ticketapi.Controller;

import com.example.ticketapi.Service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/myuser")
@RequiredArgsConstructor
public class MyUserController {


    private final MyUserService myUserService;
}

