package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Test
    public void should_employee_service_return_all_employees_when_get_all_given_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "test", 18, "Male", 10000));
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);

        //when
        List<Employee> actualEmployees = employeeService.getAll();

        //then
        assertEquals(employeeList, actualEmployees);
    }

    @Test
    public void should_return_created_employee_when_create_employee_given_a_employee() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        employeeService.save(employee);
        final ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());
        //then
        assertEquals(employee, employeeArgumentCaptor.getValue());
    }

    @Test
    public void should_return_updated_employee_when_update_employee_given_an_employee_and_valid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        //when
        employeeService.update("1", employee);
        final ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());
        //then
        assertEquals(employee, employeeArgumentCaptor.getValue());
    }

    @Test
    public void should_return_404_error_when_update_employee_given_an_employee_and_invalid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> employeeService.update("1", employee));
    }

    @Test
    public void should_delete_employee_when_delete_employee_given_an_valid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("1", "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        //when
        employeeService.delete("1");
        //then
        Mockito.verify(employeeRepository, times(1)).deleteById("1");
    }

    @Test
    public void should_return_404_error_when_delete_employee_given_an_invalid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> employeeService.delete("1"));
    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_valid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("1", "test1", 18, "Male", 10000);
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        //when
        Employee actual = employeeService.getEmployeeByID("1");
        //then
        assertEquals(employee, actual);
    }

    @Test
    public void should_return_404_error_when_get_employee_by_id_given_invalid_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> employeeService.getEmployeeByID("1"));
    }

    @Test
    public void should_return_employees_with_specific_gender_when_get_employee_given_specific_gender() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee1 = new Employee("1", "test1", 18, "Male", 10000);
        Employee employee2 = new Employee("2", "test2", 18, "Male", 10000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findAllByGender("Male")).thenReturn(employees);
        //when
        List<Employee> actual = employeeService.getEmployeesByGender("Male");
        //then
        assertEquals(employees, actual);

    }

    @Test
    public void should_return_employees_when_get_employee_given_specific_page_and_page_size() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee("3", "test2", 18, "Female", 10000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Page<Employee> page = new PageImpl<>(employees);
        when(employeeRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);
        //when
        Page<Employee> actual = employeeService.getEmployeesByPageAndPageSize(0, 1);
        //then
        assertEquals(page, actual);

    }


}
