package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAllCompanies(){
        return companies;
    }

    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company){
        companies.add(company);
        return company;
    }
    @PutMapping("/{companyID}")
    public Company updateCompany(@PathVariable int companyID, @RequestBody Company companyUpdate){
        companies.stream().filter(company -> companyID==company.getCompanyID()).findFirst().ifPresent(company -> {
            companies.remove(company);
            companies.add(companyUpdate);
        });
        return companyUpdate;
    }
    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable int companyID){
        companies.stream().filter(company -> companyID==company.getCompanyID()).findFirst().ifPresent(company -> companies.remove(company));
    }
}
