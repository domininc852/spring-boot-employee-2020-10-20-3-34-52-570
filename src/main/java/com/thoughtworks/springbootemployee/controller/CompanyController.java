package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Company getCompanyWithID(@PathVariable String companyID) {
        return companyService.getCompanyByID(companyID);

    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Company> getCompaniesByPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return companyService.getCompaniesByPageAndPageSize(page, pageSize);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getEmployeesByCompanyID(@PathVariable String companyID) {
        return companyService.getEmployeesByCompanyID(companyID);

    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping("/{companyID}")
    public Company updateCompany(@PathVariable String companyID, @RequestBody Company companyUpdate) {
        return companyService.update(companyID, companyUpdate);
    }

    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable String companyID) {
        companyService.delete(companyID);
    }
}
