package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository1 employeeRepository;
    private static final String EMPLOYEE_ID_NOT_FOUND = "Employee ID not found";

    public EmployeeService(EmployeeRepository1 employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(String employeeID, Employee employeeUpdate) {
        Optional<Employee> employeeToUpdate = getAll().stream().filter(employee -> employee.getId().equals(employeeID) ).findFirst();
        if (employeeToUpdate.isPresent()) {
            employeeRepository.deleteById(employeeID);
            return employeeRepository.save(employeeUpdate);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND);
    }

    public void delete(String employeeID) {
        employeeRepository.deleteById(employeeID);
    }

    public Employee getEmployeeByID(String employeeID) {
        return employeeRepository.findById(employeeID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesByPageAndPageSize(int page, int pageSize) {
        return getAll().stream().skip(page * pageSize).limit(pageSize).collect(Collectors.toList());
    }
}
