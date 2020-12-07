package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final String EMPLOYEE_ID_NOT_FOUND = "Employee ID not found";

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeService() {
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(String employeeID, Employee employeeUpdate) throws EmployeeNotFoundException {
        Employee employee = getEmployeeByID(employeeID);
        employeeUpdate.setId(employee.getId());
        return employeeRepository.save(employeeUpdate);
    }

    public void delete(String employeeID) throws EmployeeNotFoundException {
        getEmployeeByID(employeeID);
        employeeRepository.deleteById(employeeID);
    }

    public Employee getEmployeeByID(String employeeID) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeID)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Page<Employee> getEmployeesByPageAndPageSize(int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return employeeRepository.findAll(paging);
    }
}
