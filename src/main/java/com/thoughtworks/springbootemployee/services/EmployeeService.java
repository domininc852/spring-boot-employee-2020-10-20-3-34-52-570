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

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(int employeeID, Employee employeeUpdate) {
        return employeeRepository.update(employeeID, employeeUpdate);
    }

    public void delete(int employeeID) {
        employeeRepository.delete(employeeID);
    }

    public Employee getEmployeeByID(int employeeID) {
        return employeeRepository.getEmployeeByID(employeeID);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.getEmployeesByGender(gender);
    }

    public List<Employee> getEmployeesByPageAndPageSize(int page, int pageSize) {
        return employeeRepository.getEmployeesByPageAndPageSize(page, pageSize);
    }
}
