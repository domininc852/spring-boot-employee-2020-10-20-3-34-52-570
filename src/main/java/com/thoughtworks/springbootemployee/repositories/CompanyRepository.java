package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final String COMPANY_ID_NOT_FOUND = "companyID not found";
    private List<Company> companies = new ArrayList<>();

    public List<Company> getAll() {
        return companies;
    }

    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    public Company update(int companyID, Company companyUpdate) {
        Optional<Company> companyToUpdate = companies.stream().filter(company -> companyID == company.getId()).findFirst();
        if (companyToUpdate.isPresent()) {
            companies.remove(companyToUpdate.get());
            companies.add(companyUpdate);
            return companyUpdate;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
    }

    public void delete(int companyID) {
        Optional<Company> companyToDelete = companies.stream().filter(company -> companyID == company.getId()).findFirst();
        if (companyToDelete.isPresent()) {
            companies.remove(companyToDelete.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND);
        }
    }

    public Company getCompanyByID(int companyID) {
        return companies.stream().
                filter(company -> company.getId() == companyID).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByCompanyID(int companyID) {
        return Objects.requireNonNull(companies.stream().
                filter(company -> company.getId() == companyID).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, COMPANY_ID_NOT_FOUND)).getEmployees());
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        return companies.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }
}
