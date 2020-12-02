package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return null;
    }
}
