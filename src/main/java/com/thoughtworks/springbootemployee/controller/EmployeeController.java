package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeWithID(@PathVariable int employeeID) {
        return employeeService.getEmployeeWithID(employeeID);
    }

    @GetMapping(params = "gender")
    public List<Employee> filterEmployeesWithGender(@RequestParam(name = "gender", required = false) String gender) {
        return employeeService.getEmployeesWithGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> filterEmployeesWithPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return employeeService.getEmployeesWithPageAndPageSize(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee(@PathVariable int employeeID, @RequestBody Employee employeeUpdate) {
        return employeeService.update(employeeID, employeeUpdate);
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployee(@PathVariable int employeeID) {
        employeeService.delete(employeeID);
    }

}
