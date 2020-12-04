package com.thoughtworks.springbootemployee.repositories;


import com.thoughtworks.springbootemployee.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
    List<Employee> findAllByGender(String gender);
}
