package com.thoughtworks.springbootemployee.exceptions;

public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
