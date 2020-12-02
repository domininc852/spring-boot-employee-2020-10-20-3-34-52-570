package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees=new ArrayList<>();

    public EmployeeController() {
        this.employees.add(new Employee(1,"abc",10,"Male",10000) );
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return this.employees;
    }
}
