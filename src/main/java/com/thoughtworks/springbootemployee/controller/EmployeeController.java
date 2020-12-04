package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
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
    public Employee getEmployeeByID(@PathVariable String employeeID) throws EmployeeNotFoundException {
        return employeeService.getEmployeeByID(employeeID);
    }

    @GetMapping(params = "gender")
    public List<Employee> getEmployeeByGender(@RequestParam(name = "gender", required = false) String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return employeeService.getEmployeesByPageAndPageSize(page, pageSize).getContent();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee(@PathVariable String employeeID, @RequestBody Employee employeeUpdate) throws EmployeeNotFoundException {
        return employeeService.update(employeeID, employeeUpdate);
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployee(@PathVariable String employeeID) throws EmployeeNotFoundException {
        employeeService.delete(employeeID);
    }

}
