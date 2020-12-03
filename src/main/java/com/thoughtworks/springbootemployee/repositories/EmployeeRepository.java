package com.thoughtworks.springbootemployee.repositories;


import com.thoughtworks.springbootemployee.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
    List<Employee> findAllByGender(String gender);
}
