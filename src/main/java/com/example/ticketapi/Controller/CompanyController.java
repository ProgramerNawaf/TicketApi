package com.example.ticketapi.Controller;


import com.example.ticketapi.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


}
