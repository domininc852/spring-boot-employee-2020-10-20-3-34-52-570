package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        employeeList.add(new Employee(1, "test", 18, "Male", 10000));
        Mockito.when(employeeRepository.getAll()).thenReturn(employeeList);

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
        Employee employee = new Employee(1, "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.create(employee)).thenReturn(employee);

        //when
        employeeService.create(employee);
        final ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository, times(1)).create(employeeArgumentCaptor.capture());
        //then
        final Employee actual = employeeArgumentCaptor.getValue();
        assertEquals(employee, actual);
    }

    @Test
    public void should_return_updated_employee_when_update_employee_given_a_employee_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee(1, "test", 18, "Male", 10000);
        Mockito.when(employeeRepository.update(1, employee)).thenReturn(employee);

        //when
        Employee actual = employeeService.update(1, employee);
        //then
        assertEquals(employee, actual);
    }
    @Test
    public void should_delete_employee_when_delete_employee_given_a_employee_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee(1, "test", 18, "Male", 10000);

        //when
        employeeService.delete(1);
        //then
        Mockito.verify(employeeRepository,times(1)).delete(1);
    }
    @Test
    public void should_return_employee_when_get_employee_with_id_given_employeeID() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee1 = new Employee(1, "test1", 18, "Male", 10000);
        when(employeeRepository.getEmployeeWithID(1)).thenReturn(employee1);
        //when
        Employee actual = employeeService.getEmployeeWithID(1);
        //then
        assertEquals(employee1,actual);
    }
    @Test
    public void should_return_employees_with_specific_gender_when_get_employee_given_specific_gender() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee1 = new Employee(1, "test1", 18, "Male", 10000);
        Employee employee2 = new Employee(2, "test2", 18, "Male", 10000);
        Employee employee3 = new Employee(3, "test2", 18, "Female", 10000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.getEmployeesWithGender("Male")).thenReturn(employees);
        //when
        List<Employee> actual = employeeService.getEmployeesWithGender("Male");
        //then
        assertEquals(employees,actual);

    }





}
