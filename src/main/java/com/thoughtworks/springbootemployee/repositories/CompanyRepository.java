package com.thoughtworks.springbootemployee.repositories;


import com.thoughtworks.springbootemployee.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
}
