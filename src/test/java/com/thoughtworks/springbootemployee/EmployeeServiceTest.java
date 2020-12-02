package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    @Test
    public void should_employee_service_return_all_employees_when_get_all_given_employees() {
        //given
        EmployeeService employeeService = new EmployeeService();
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());
        Mockito.when(employeeRepository.getAll()).thenReturn(employeeList);

        //when
        List<Employee> actualEmployees = employeeService.getAll();

        //then
        assertEquals(employeeList,actualEmployees);
    }
}
