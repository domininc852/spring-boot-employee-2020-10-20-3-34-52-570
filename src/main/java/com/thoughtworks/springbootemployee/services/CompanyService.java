package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    public Company create(Company company) {
        return companyRepository.create(company);
    }

    public Company update(int companyID, Company companyUpdate) {
        return companyRepository.update(companyID, companyUpdate);
    }

    public void delete(int companyID) {
        companyRepository.delete(companyID);
    }

    public Company getCompanyWithID(int companyID) {
        return companyRepository.getCompanyWithID(companyID);
    }

    public List<Employee> getEmployeesWithCompanyID(int companyID) {
        return companyRepository.getEmployeesWithCompanyID(companyID);
    }

    public List<Company> getCompaniesWithPageAndPageSize(int page, int pageSize) {
        return companyRepository.getCompaniesWithPageAndPageSize(page, pageSize);
    }
}
