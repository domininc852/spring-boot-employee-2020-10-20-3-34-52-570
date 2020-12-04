package com.thoughtworks.springbootemployee.dto;

import java.util.List;

public class CompanyRequest {
    private String name;
    private List<String> employeeIDs;

    public CompanyRequest(String name, List<String> employeeIDs) {
        this.name = name;
        this.employeeIDs = employeeIDs;
    }

    public CompanyRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmployeeIDs() {
        return employeeIDs;
    }

    public void setEmployeeIDs(List<String> employeeIDs) {
        this.employeeIDs = employeeIDs;
    }
}
