package com.thoughtworks.springbootemployee.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Company {
    private String name;
    private int employeesNumber;
    private List<String> employeeIDs = new ArrayList<>();
    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    public Company(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Company(String name, List<String> employeeIDs) {
        this.name = name;
        this.employeeIDs = employeeIDs;
        employeesNumber = employeeIDs.size();
    }


    public Company() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
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


    public String getId() {
        return id;
    }

    public void setEmployeeIDs(List<String> employeeIDs) {
        this.employeeIDs = employeeIDs;
    }

    public List<String> getEmployeeIDs() {
        return employeeIDs;
    }
}
