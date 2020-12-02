package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();
    public List<Company> getAll() {
        return companies;
    }

    public Company create(Company company) {
        companies.add(company);
        return company;
    }

    public Company update(int companyID, Company companyUpdate) {
        companies.stream().filter(company -> companyID == company.getId()).findFirst().ifPresent(company -> {
            companies.remove(company);
            companies.add(companyUpdate);
        });
        return companyUpdate;
    }

    public void delete(int companyID) {
        companies.stream().filter(company -> companyID == company.getId()).findFirst().ifPresent(company -> companies.remove(company));
    }

    public Company getCompanyWithID(int companyID) {
        return companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null);
    }

    public List<Employee> getEmployeesWithCompanyID(int companyID) {
        return Objects.requireNonNull(companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null)).getEmployees();
    }
}
