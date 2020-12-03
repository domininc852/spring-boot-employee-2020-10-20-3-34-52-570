package com.thoughtworks.springbootemployee;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    private String name;
    private int employeesNumber;
    @DBRef
    private List<Employee> employees;
    @MongoId( value = FieldType.OBJECT_ID)
    private String id;

    public Company(String name, int employeesNumber, List<Employee> employees, String id) {
        this.name = name;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
        this.id = id;
    }

    public Company() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setId(String id) {
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

    public String getId() {
        return id;
    }
}
