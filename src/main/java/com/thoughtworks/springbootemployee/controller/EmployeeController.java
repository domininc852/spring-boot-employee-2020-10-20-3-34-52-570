package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;


    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAll().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{employeeID}")
    public EmployeeResponse getEmployeeByID(@PathVariable String employeeID) {
        return employeeMapper.toResponse(employeeService.getEmployeeByID(employeeID));
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getEmployeeByGender(@RequestParam(name = "gender", required = false) String gender) {
        return employeeService.getEmployeesByGender(gender).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPageNumberAndPageSize(@RequestParam(name = "page", required = false) int page, @RequestParam(name = "pageSize", required = false) int pageSize) {
        return employeeService.getEmployeesByPageAndPageSize(page, pageSize).getContent()
                .stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.save(employee));
    }

    @PutMapping("/{employeeID}")
    public EmployeeResponse updateEmployee(@PathVariable String employeeID, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.update(employeeID, employee));
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployee(@PathVariable String employeeID) {
        employeeService.delete(employeeID);
    }

}
