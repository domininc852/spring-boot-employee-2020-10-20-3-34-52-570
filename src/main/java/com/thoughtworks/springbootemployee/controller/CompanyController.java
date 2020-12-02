package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAllCompanies() {
        return companies;
    }

    @GetMapping("/{companyID}")
    public Company getCompanyWithID(@PathVariable int companyID) {
        return companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null);

    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> filterCompaniesWithPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return companies.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getEmployeesWithCompanyID(@PathVariable int companyID) {
        return Objects.requireNonNull(companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null)).getEmployees();

    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        companies.add(company);
        return company;
    }

    @PutMapping("/{companyID}")
    public Company updateCompany(@PathVariable int companyID, @RequestBody Company companyUpdate) {
        companies.stream().filter(company -> companyID == company.getId()).findFirst().ifPresent(company -> {
            companies.remove(company);
            companies.add(companyUpdate);
        });
        return companyUpdate;
    }

    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable int companyID) {
        companies.stream().filter(company -> companyID == company.getId()).findFirst().ifPresent(company -> companies.remove(company));
    }
}
