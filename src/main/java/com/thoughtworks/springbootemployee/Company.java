package com.thoughtworks.springbootemployee;

import java.util.List;

public class Company {
    private String name;
    private int employeesNumber;
    private List<Employee> employees;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public int getId() {
        return id;
    }
}