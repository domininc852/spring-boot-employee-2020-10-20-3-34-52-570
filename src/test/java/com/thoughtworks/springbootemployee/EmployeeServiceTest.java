package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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




}
