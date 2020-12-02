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
    public Employee create(Employee employee){
        return employeeRepository.create(employee);
    }

    public Employee update(int employeeID, Employee employeeUpdate) {
        return employeeRepository.update(employeeID,employeeUpdate);
    }

    public void delete(int employeeID) {
        employeeRepository.delete(employeeID);
    }

    public Employee getEmployeeWithID(int employeeID) {
        return employeeRepository.getEmployeeWithID(employeeID);
    }

    public List<Employee> getEmployeesWithGender(String gender) {
        return employeeRepository.getEmployeesWithGender(gender);
    }
}
