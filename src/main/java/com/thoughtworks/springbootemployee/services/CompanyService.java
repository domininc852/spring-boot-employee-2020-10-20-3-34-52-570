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

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company update(int companyID, Company companyUpdate) {
        return companyRepository.update(companyID, companyUpdate);
    }

    public void delete(int companyID) {
        companyRepository.delete(companyID);
    }

    public Company getCompanyByID(int companyID) {
        return companyRepository.getCompanyByID(companyID);
    }

    public List<Employee> getEmployeesByCompanyID(int companyID) {
        return companyRepository.getEmployeesByCompanyID(companyID);
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyRepository.getCompaniesByPageAndPageSize(page, pageSize);
    }
}
