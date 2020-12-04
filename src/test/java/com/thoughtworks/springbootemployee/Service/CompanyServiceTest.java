package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.entities.Company;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.CompanyService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    @Test
    public void should_company_service_return_all_companies_when_get_all_given_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("ABC", Collections.singletonList(employee.getId())));
        Mockito.when(companyRepository.findAll()).thenReturn(companies);

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
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.save(company)).thenReturn(company);

        //when
        companyService.save(company);
        final ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());
        //then
        assertEquals(company, companyArgumentCaptor.getValue());
    }

    @Test
    public void should_return_updated_company_when_update_company_given_a_company_and_valid_companyID() throws CompanyNotFoundException {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        Mockito.when(companyRepository.save(any())).thenReturn((company));

        //when
        companyService.update("1", company);
        final ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());
        //then
        assertEquals(company, companyArgumentCaptor.getValue());
    }

    @Test
    public void should_return_404_error_when_update_company_given_a_company_and_invalid_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> companyService.update("1", company));
    }

    @Test
    public void should_delete_company_when_delete_company_given_valid_companyID() throws CompanyNotFoundException {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.of(company));

        //when
        companyService.delete("1");
        //then
        Mockito.verify(companyRepository, times(1)).deleteById("1");
    }

    @Test
    public void should_return_404_error_when_delete_company_given_invalid_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> companyService.delete("1"));
    }

    @Test
    public void should_return_company_when_get_company_with_id_given_valid_companyID() throws CompanyNotFoundException {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        //when
        Company actual = companyService.getCompanyByID("1");
        //then
        assertEquals(company, actual);
    }

    @Test
    public void should_return_404_error_when_get_company_with_id_given_invalid_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompanyByID("1"));
    }

    @Test
    public void should_return_employees_when_get_employees_by_company_id_given_valid_companyID() throws CompanyNotFoundException {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, employeeRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        Mockito.when(employeeRepository.findAllById(any())).thenReturn(Collections.singletonList(employee));
        //when
        List<Employee> actual = companyService.getEmployeesByCompanyID("1");
        //then
        assertEquals(1, actual.size());
    }

    @Test
    public void should_return_404_error_when_get_employees_by_company_id_given_invalid_companyID() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> companyService.getEmployeesByCompanyID("1"));
    }

    @Test
    public void should_return_companies_when_get_companies_given_specific_page_and_page_size() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Company company = new Company("ABC", Collections.singletonList(employee.getId()));
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        Page<Company> page = new PageImpl<>(companies);
        when(companyRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);
        //when
        Page<Company> actual = companyService.getCompaniesByPageAndPageSize(0, 1);
        //then
        assertEquals(page, actual);

    }
}
