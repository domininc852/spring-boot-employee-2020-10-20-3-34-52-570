package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entities.Company;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAll().stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{companyID}")
    public CompanyResponse getCompanyWithID(@PathVariable String companyID) throws CompanyNotFoundException {
        return companyMapper.toResponse(companyService.getCompanyByID(companyID));

    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return companyService.getCompaniesByPageAndPageSize(page, pageSize)
                .getContent().stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{companyID}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyID(@PathVariable String companyID) throws CompanyNotFoundException {
        return companyService.getEmployeesByCompanyID(companyID)
                .stream().map(employeeMapper::toResponse).collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompanyResponse createCompany(@RequestBody CompanyRequest companyRequest) {
        return companyMapper.toResponse(companyService.save(companyMapper.toEntity(companyRequest)));
    }

    @PutMapping("/{companyID}")
    public CompanyResponse updateCompany(@PathVariable String companyID, @RequestBody CompanyRequest companyRequest) throws CompanyNotFoundException {
        return companyMapper.toResponse(companyService.update(companyID, companyMapper.toEntity(companyRequest)));
    }

    @DeleteMapping("/{companyID}")
    public void deleteCompany(@PathVariable String companyID) throws CompanyNotFoundException {
        companyService.delete(companyID);
    }
}
