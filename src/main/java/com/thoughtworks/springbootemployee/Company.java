package com.thoughtworks.springbootemployee;

import java.util.List;

public class Company {
    private String companyName;
    private int employeeNumber;
    private List<Employee> employees;
    private int id;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public int getId() {
        return id;
    }
}
