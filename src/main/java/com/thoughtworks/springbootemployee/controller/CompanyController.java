package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
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
    public Company addCompany(@RequestBody Company company){
        companies.add(company);
        return company;
    }
}
