package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();


    @GetMapping
    public List<Employee> getAllEmployees() {
        return this.employees;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee(@PathVariable int employeeID, @RequestBody Employee employeeUpdate) {
        Optional<Employee> employeeToUpdate = employees.stream().filter(employee -> employee.getId() == employeeID).findFirst();
        if (employeeToUpdate.isPresent()) {
            employees.remove(employeeToUpdate.get());
            employees.add(employeeUpdate);
        }

        return employeeUpdate;
    }
    @DeleteMapping("/{employeeID}")
    public void deleteEmployee(@PathVariable int employeeID){
        Optional<Employee> employeeToUpdate = employees.stream().filter(employee -> employee.getId() == employeeID).findFirst();
        employeeToUpdate.ifPresent(employee -> employees.remove(employee));
    }

}
