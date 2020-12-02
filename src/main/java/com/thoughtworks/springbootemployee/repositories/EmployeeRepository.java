package com.thoughtworks.springbootemployee.repositories;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    public List<Employee> getAll() {
        return null;
    }

    public Employee create(Employee employee) {
        return employee;
    }

    public Employee update(int id, Employee employee) {
        return employee;
    }
}
