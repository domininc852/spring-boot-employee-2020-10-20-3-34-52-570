package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final String EMPLOYEE_ID_NOT_FOUND = "Employee ID not found";
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getAll() {
        return employees;
    }

    public Employee create(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee update(int employeeID, Employee employeeUpdate) {
        Optional<Employee> employeeToUpdate = employees.stream().filter(employee -> employee.getId() == employeeID).findFirst();
        if (employeeToUpdate.isPresent()) {
            employees.remove(employeeToUpdate.get());
            return employeeUpdate;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND);

    }

    public void delete(int employeeID) {
        Optional<Employee> employeeToDelete = employees.stream().filter(employee -> employee.getId() == employeeID).findFirst();
        if (employeeToDelete.isPresent()) {
            employees.remove(employeeToDelete.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND);
        }
    }

    public Employee getEmployeeWithID(int employeeID) {
        return employees.stream().
                filter(employee -> employee.getId() == employeeID).findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesWithGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithPageAndPageSize(int page, int pageSize) {
        return employees.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }
}
