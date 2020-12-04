package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entities.Employee;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String name;
    private int employeesNumber;
    private List<Employee> employees;

    public CompanyResponse(String id, String name, int employeesNumber, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public CompanyResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
