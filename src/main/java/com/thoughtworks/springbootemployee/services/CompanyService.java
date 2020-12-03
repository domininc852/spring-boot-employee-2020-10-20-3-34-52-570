package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    private static final String COMPANY_ID_NOT_FOUND = "companyID not found";


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company update(String companyID, Company companyUpdate) {
        Optional<Company> companyToUpdate = companyRepository.findById(companyID);
        if (companyToUpdate.isPresent()) {
            companyUpdate.setId(companyToUpdate.get().getId());
            return companyRepository.save(companyUpdate);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
    }

    public void delete(String companyID) {
        Optional<Company> companyToDelete = companyRepository.findById(companyID);
        if (companyToDelete.isPresent()) {
            companyRepository.deleteById(companyID);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
        }


    }

    public Company getCompanyByID(String companyID) {
        return companyRepository.findById(companyID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByCompanyID(String companyID) {
        return companyRepository.findById(companyID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND)).getEmployees();
    }

    public Page<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        Pageable paging = PageRequest.of(page,pageSize);
        return companyRepository.findAll(paging);
    }
}
