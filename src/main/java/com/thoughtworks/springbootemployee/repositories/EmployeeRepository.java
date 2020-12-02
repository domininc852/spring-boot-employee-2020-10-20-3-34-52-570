package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getAll() {
        return employees;
    }

    public Employee create(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee update(int employeeID, Employee employeeUpdate) {
        employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().ifPresent(employee -> {
            employees.remove(employee);
            employees.add(employeeUpdate);
        });
        return employeeUpdate;

    }

    public void delete(int employeeID) {
        employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().ifPresent(employee -> employees.remove(employee));
    }

    public Employee getEmployeeWithID(int employeeID) {
        return employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().orElse(null);
    }

    public List<Employee> getEmployeesWithGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithPageAndPageSize(int page, int pageSize) {
        return employees.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }
}
