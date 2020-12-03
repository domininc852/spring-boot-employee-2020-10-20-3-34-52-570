package com.thoughtworks.springbootemployee.services;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(String employeeID, Employee employeeUpdate) {
        Optional<Employee> employeeToUpdate = employeeRepository.findById(employeeID);
        if (employeeToUpdate.isPresent()) {
            employeeUpdate.setId(employeeToUpdate.get().getId());
            return employeeRepository.save(employeeUpdate);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND);
    }

    public void delete(String employeeID) {
        Optional<Employee> employeeToUpdate = employeeRepository.findById(employeeID);
        if (employeeToUpdate.isPresent()) {
            employeeRepository.deleteById(employeeID);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND);
        }
    }

    public Employee getEmployeeByID(String employeeID) {
        return employeeRepository.findById(employeeID)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, EMPLOYEE_ID_NOT_FOUND));
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Page<Employee> getEmployeesByPageAndPageSize(int page, int pageSize) {
        Pageable paging = PageRequest.of(page,pageSize);
        return employeeRepository.findAll(paging);
    }
}
