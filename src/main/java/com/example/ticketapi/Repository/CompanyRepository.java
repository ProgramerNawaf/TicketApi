package com.example.ticketapi.Repository;

import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    Company findCompanyById(Integer id);
    Company findCompanyByName(String name);
    Company findCompanyByEventsContains(Event event);


}
