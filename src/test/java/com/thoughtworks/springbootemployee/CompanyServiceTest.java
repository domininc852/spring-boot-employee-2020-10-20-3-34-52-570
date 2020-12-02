package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.CompanyService;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

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
    @Test
    public void should_return_created_employee_when_create_employee_given_a_employee() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        Company company = new Company("ABC",1,employees,1);
        Mockito.when(companyRepository.create(company)).thenReturn(company);

        //when
        companyService.create(company);
        final ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyRepository, times(1)).create(companyArgumentCaptor.capture());
        //then
        final Company actual = companyArgumentCaptor.getValue();
        assertEquals(company, actual);
    }
    @Test
    public void should_return_updated_employee_when_update_employee_given_a_employee_employeeID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        Company company = new Company("ABC",1,employees,1);
        Mockito.when(companyRepository.update(1,company)).thenReturn(company);

        //when
        Company actual = companyService.update(1, company);
        //then
        assertEquals(company, actual);
    }
}
