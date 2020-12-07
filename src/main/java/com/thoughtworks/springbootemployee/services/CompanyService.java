package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entities.Company;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final String COMPANY_ID_NOT_FOUND = "companyID not found";


    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyService() {
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company update(String companyID, Company companyUpdate) {
        Company company = getCompanyByID(companyID);
        companyUpdate.setId(company.getId());
        companyUpdate.setEmployeesNumber(companyUpdate.getEmployeeIDs().size());
        return companyRepository.save(companyUpdate);
    }

    public void delete(String companyID) {
        getCompanyByID(companyID);
        companyRepository.deleteById(companyID);
    }

    public Company getCompanyByID(String companyID) {
        return companyRepository.findById(companyID)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByCompanyID(String companyID) {
        Company company = getCompanyByID(companyID);
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAllById(company.getEmployeeIDs()).forEach(employees::add);
        return employees;
    }

    public Page<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return companyRepository.findAll(paging);
    }
}
