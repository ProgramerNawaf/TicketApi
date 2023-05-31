package com.example.ticketapi.Service;


import com.example.ticketapi.ApiException.ApiException;
import com.example.ticketapi.Model.Company;
import com.example.ticketapi.Model.Event;
import com.example.ticketapi.Model.MyUser;
import com.example.ticketapi.Repository.CompanyRepository;
import com.example.ticketapi.Repository.EventRepository;
import com.example.ticketapi.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EventRepository eventRepository;
    private final MyUserRepository myUserRepository;
    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public void addCompany(Company company, Integer userId){
        MyUser myUser = myUserRepository.findMyUserById(userId);
        if (!myUser.getRole().equals("admin")){
            throw new ApiException("Only Admin Can add Company");
        }
        companyRepository.save(company);
    }

    public void updateCompany(Company company,Integer userId){
        MyUser myUser = myUserRepository.findMyUserById(userId);
        if (!myUser.getRole().equals("admin")){
            throw new ApiException("Only Admin Can add Company");
        }
        Company oldCompany = companyRepository.findCompanyById(company.getId());
        if (oldCompany == null){
            throw new ApiException("Company Not Found");
        }

        oldCompany.setName(company.getName());
        oldCompany.setRevenue(company.getRevenue());
        companyRepository.save(oldCompany);
    }

    public void deleteCompany(Integer companyId, Integer userId){
        MyUser myUser = myUserRepository.findMyUserById(userId);
        if (!myUser.getRole().equals("admin")){
            throw new ApiException("Only Admin Can add Company");
        }
        Company company = companyRepository.findCompanyById(companyId);
        if (company == null){
            throw new ApiException("Company Not Found");
        }
        companyRepository.delete(company);
    }

    public Set<Event> getCompanyEvents(String companyName){
        Company company = companyRepository.findCompanyByName(companyName);
        if (company == null){
            throw new ApiException("Company Not Found");
        }
        return company.getEvents();
    }
    public List<String> getCompanyNames(Integer adminId){
        List<Company> company = companyRepository.findAll();
        if (company == null){
            throw new ApiException("Company Not Found");
        }
        List<String> compnayNames= null;

        for(int i = 0 ; i<company.size();i++)
            compnayNames.add(company.get(i).getName());
        return compnayNames;
    }











}
