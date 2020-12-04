package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handBadRequest(IllegalArgumentException exception){
        return new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.name());
    }
    @ExceptionHandler({CompanyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyNotFound(CompanyNotFoundException exception){
        return new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.name());
    }
    @ExceptionHandler({EmployeeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException exception){
        return new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.name());
    }

}
