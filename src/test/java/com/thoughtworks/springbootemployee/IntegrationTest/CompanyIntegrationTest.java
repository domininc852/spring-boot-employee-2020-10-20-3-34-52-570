package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        List<Employee> employees =new ArrayList<>();
        employees.add(employeeRepository.save(new Employee("bar", 20, "Female", 120)));
        Company company = new Company("ABC", 1,employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies")).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].name").value("ABC"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees[0].id").value(employees.get(0).getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value("bar"))
                .andExpect(jsonPath("$[0].employees[0].age").value(20))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(120));
    }
    @Test
    public void should_return_created_company_when_create_given_company() throws Exception {
        //given
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        String companyAsJson="{\n" +
                "    \"name\": \"ABC\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [\n" +
                "     "+employee.toJSON()+"\n"+
                "    ]\n" +
                "}";
        //when
        //then
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("ABC"))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees[0].id").value(employee.getId()))
                .andExpect(jsonPath("$.employees[0].name").value("bar"))
                .andExpect(jsonPath("$.employees[0].age").value(20))
                .andExpect(jsonPath("$.employees[0].gender").value("Female"))
                .andExpect(jsonPath("$.employees[0].salary").value(120));
    }
    @Test
    public void should_return_updated_company_when_update_given_company() throws Exception {
        //given
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees =new ArrayList<>();
        employees.add(employee);
        Company company = new Company("ABC", 1,employees);
        companyRepository.save(company);

        String companyAsJson="{\n" +
                "    \"name\": \"ABCD\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [\n" +
                "     "+employee.toJSON()+"\n"+
                "    ]\n" +
                "}";
        //when
        //then
        mockMvc.perform(put("/companies/"+company.getId()).contentType(MediaType.APPLICATION_JSON).content(companyAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value("ABCD"))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees[0].id").value(employee.getId()))
                .andExpect(jsonPath("$.employees[0].name").value("bar"))
                .andExpect(jsonPath("$.employees[0].age").value(20))
                .andExpect(jsonPath("$.employees[0].gender").value("Female"))
                .andExpect(jsonPath("$.employees[0].salary").value(120));
    }

}
