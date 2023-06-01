package com.example.ticketapi.Controller;


import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Service.CompanyService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/get")
    public ResponseEntity getCompanies(){
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.status(200).body(companies);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity addCompany(@Valid @RequestBody Company company, @PathVariable Integer userId){
        companyService.addCompany(company,userId);
        return ResponseEntity.status(200).body("Company Added");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateCompany(@Valid @RequestBody Company company, @PathVariable Integer userId){
        companyService.updateCompany(company,userId);
        return ResponseEntity.status(200).body("Company Updated");
    }

    @DeleteMapping ("/delete/{companyId}/{userId}")
    public ResponseEntity deleteCompany(@PathVariable Integer companyId , @PathVariable Integer userId){
        companyService.deleteCompany(companyId, userId);
        return ResponseEntity.status(200).body("Company Deleted");
    }

    @GetMapping("/events/{companyName}")
    public ResponseEntity getCompanyEvents(@PathVariable String companyName){
       Set<Event> events = companyService.getCompanyEvents(companyName);
        return ResponseEntity.status(200).body(events);
    }
    @GetMapping("/get-company/{adminId}")
    public ResponseEntity getCompanyNames(@PathVariable Integer adminId){
        List<String> events = companyService.getCompanyNames(adminId);
        return ResponseEntity.status(200).body(events);
    }
    @GetMapping("/get-company-by-name/{name}")
    public ResponseEntity getCompanyNames(@PathVariable String name){
        Company company =companyService.getCompanyByName(name);
        return ResponseEntity.status(200).body(company);
    }


}
