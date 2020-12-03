package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    private static final String COMPANY_ID_NOT_FOUND = "companyID not found";


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
//        Optional<Company> companyToUpdate = companies.stream().filter(company -> companyID == company.getId()).findFirst();
//        if (companyToUpdate.isPresent()) {
//            companies.remove(companyToUpdate.get());
//            companies.add(companyUpdate);
//            return companyUpdate;
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
        return null;
    }

    public void delete(int companyID) {
        Company company = companyRepository.getCompanyByID(companyID);
        if (company==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
        }
        companyRepository.delete(companyID);
    }

    public Company getCompanyByID(int companyID) {
        Company company = companyRepository.getCompanyByID(companyID);
        if (company==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
        }
        return company;
    }

    public List<Employee> getEmployeesByCompanyID(int companyID) {
        return Objects.requireNonNull(companyRepository.getAll().stream().
                filter(company -> company.getId() == companyID).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND)).getEmployees());
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyRepository.getAll().stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList());
    }
}
