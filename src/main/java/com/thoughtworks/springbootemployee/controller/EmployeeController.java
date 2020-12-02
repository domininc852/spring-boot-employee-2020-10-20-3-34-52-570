package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private List<Employee> employees = new ArrayList<>();


    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeWithID(@PathVariable int employeeID) {
        return employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> filterEmployeesWithGender(@RequestParam(name = "gender", required = false) String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> filterEmployeesWithPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return employees.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeService.create(employee);
        return employee;
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee(@PathVariable int employeeID, @RequestBody Employee employeeUpdate) {
       return employeeService.update(employeeID,employeeUpdate);
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployee(@PathVariable int employeeID) {
        employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().ifPresent(employee -> employees.remove(employee));
    }

}
