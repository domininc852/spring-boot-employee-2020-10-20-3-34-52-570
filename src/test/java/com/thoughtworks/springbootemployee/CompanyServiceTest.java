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
import static org.mockito.Mockito.when;

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
    public void should_return_created_company_when_create_company_given_a_company() {
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
    public void should_return_updated_company_when_update_company_given_a_company_companyID() {
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
    @Test
    public void should_delete_company_when_delete_company_given_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        Company company = new Company("ABC",1,employees,1);

        //when
        companyService.delete(1);
        //then
        Mockito.verify(companyRepository,times(1)).delete(1);
    }
    @Test
    public void should_return_company_when_get_company_with_id_given_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        Company company = new Company("ABC",1,employees,1);
        when(companyRepository.getCompanyWithID(1)).thenReturn(company);
        //when
        Company actual = companyService.getCompanyWithID(1);
        //then
        assertEquals(company,actual);
    }
    @Test
    public void should_return_employees_when_get_employees_with_company_id_given_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees =new ArrayList<>();
        employees.add(new Employee(1, "test", 18, "Male", 10000));
        Company company = new Company("ABC",1,employees,1);
        when(companyRepository.getEmployeesWithCompanyID(1)).thenReturn(employees);
        //when
        List<Employee> actual = companyService.getEmployeesWithCompanyID(1);
        //then
        assertEquals(employees,actual);
    }
    @Test
    public void should_return_companies_when_get_companies_given_specific_page_and_page_size() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> employees1 =new ArrayList<>();
        employees1.add(new Employee(1, "test1", 18, "Male", 10000));
        Company company1 = new Company("ABC",1,employees1,1);
        List<Employee> employees2 =new ArrayList<>();
        employees2.add(new Employee(2, "test2", 18, "Male", 10000));
        Company company2 = new Company("EFG",1,employees2,2);
        List<Employee> employees3 =new ArrayList<>();
        employees3.add(new Employee(3, "test3", 18, "Male", 10000));
        Company company3 = new Company("HIJ",1,employees3,3);

        List<Company> companies = new ArrayList<>();
        companies.add(company3);
        when(companyRepository.getCompaniesWithPageAndPageSize(2,2)).thenReturn(companies);
        //when
        List<Employee> actual = companyService.getCompaniesWithPageAndPageSize(2,2);
        //then
        assertEquals(companies,actual);

    }
}
