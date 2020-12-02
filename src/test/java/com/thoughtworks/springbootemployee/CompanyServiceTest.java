package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.CompanyService;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyServiceTest {
    @Test
    public void should_company_service_return_all_companies_when_get_all_given_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("ABC",1,employees,1));
        Mockito.when(companyRepository.getAll()).thenReturn(companies);

        //when
        List<Company> actualCompanies = companyService.getAll();

        //then
        assertEquals(companies, actualCompanies);
    }
}
