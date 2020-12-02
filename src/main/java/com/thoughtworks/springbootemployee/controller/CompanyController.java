package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAll();
    }

    @GetMapping("/{companyID}")
    public Company getCompanyWithID(@PathVariable int companyID) {
        return companyService.getCompanyWithID(companyID);

    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> filterCompaniesWithPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return companyService.getCompaniesWithPageAndPageSize(page, pageSize);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getEmployeesWithCompanyID(@PathVariable int companyID) {
        return companyService.getEmployeesWithCompanyID(companyID);

    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @PutMapping("/{companyID}")
    public Company updateCompany(@PathVariable int companyID, @RequestBody Company companyUpdate) {
        return companyService.update(companyID, companyUpdate);
    }

    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable int companyID) {
        companyService.delete(companyID);
    }
}
