package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entities.Company;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        if (companyRequest.getEmployeeIDs() == null) {
            company.setEmployeeIDs(new ArrayList<>());
        }
        company.setEmployeesNumber(company.getEmployeeIDs().size());
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAllById(company.getEmployeeIDs()).forEach(employees::add);
        companyResponse.setEmployees(employees);
        return companyResponse;

    }
}
