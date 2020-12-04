package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.entities.Company;
import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Company company = companyRepository.save(new Company("ABC", Collections.singletonList(employee.getId())));
        //when
        //then
        mockMvc.perform(get("/companies")).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].name").value("ABC"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees[0].id").value(employee.getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value("bar"))
                .andExpect(jsonPath("$[0].employees[0].age").value(20))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(120));
    }

    @Test
    public void should_return_created_company_when_create_given_company() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        String companyAsJson = "{\n" +
                "    \"name\": \"ABC\",\n" +
                "    \"employeeIDs\": [\n" +
                "     \"" + employee.getId() + "\" \n" +
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
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Company company = companyRepository.save(new Company("ABC", Collections.singletonList(employee.getId())));
        companyRepository.save(company);
        String companyAsJson = "{\n" +
                "    \"name\": \"ABCD\",\n" +
                "    \"employeeIDs\": [\n" +
                "     \"" + employee.getId() + "\" \n" +
                "    ]\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/companies/" + company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyAsJson))
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

    @Test
    public void should_return_404_error_when_update_given_nonexist_companyID() throws Exception {
        //given
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(put("/companies/" + fakeID).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void should_return_400_error_when_update_given_invalid_format_companyID() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(put("/companies/" + "1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void should_delete_company_when_delete_given_valid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Company company = companyRepository.save(new Company("ABC", Collections.singletonList(employee.getId())));
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isOk());
        assertTrue(companyRepository.findById(company.getId()).isEmpty());
    }

    @Test
    public void should_return_404_error_when_delete_given_nonexist_companyID() throws Exception {
        //given
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(delete("/companies/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_400_error_when_delete_given_invalid_format_companyID() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/companies/" + "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_company_when_get_company_by_id_given_valid_companyID() throws Exception {
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Company company = companyRepository.save(new Company("ABC", Collections.singletonList(employee.getId())));
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value("ABC"))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees[0].id").value(employee.getId()))
                .andExpect(jsonPath("$.employees[0].name").value("bar"))
                .andExpect(jsonPath("$.employees[0].age").value(20))
                .andExpect(jsonPath("$.employees[0].gender").value("Female"))
                .andExpect(jsonPath("$.employees[0].salary").value(120));
    }

    @Test
    public void should_return_404_error_when_get_company_by_id_given_nonexist_companyID() throws Exception {
        //given
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(get("/companies/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_404_error_when_get_company_by_id_given_invalid_format_companyID() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/companies/" + "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_all_employees_when_get_employees_by_id_given_valid_companyID() throws Exception {
        //given
        Employee employee1 = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Employee employee2 = employeeRepository.save(new Employee("abcd", 18, "Male", 120));
        Company company = companyRepository.save(new Company("ABC", Arrays.asList(employee1.getId(), employee2.getId())));
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + company.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].name").value("bar"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(120))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(jsonPath("$[1].name").value("abcd"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(120));
    }

    @Test
    public void should_return_404_error_when_get_employees_by_id_given_nonexist_companyID() throws Exception {
        //given
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(get("/companies/" + fakeID + "/employees"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_400_error_when_get_employees_by_id_given_invalid_format_companyID() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/companies/1/employees"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_paged_companies_when_get_companies_by_page_and_page_size_given_page_and_page_size() throws Exception {
        //given
        Employee employee1 = employeeRepository.save(new Employee("a", 18, "Female", 120));
        Employee employee2 = employeeRepository.save(new Employee("b", 18, "Male", 120));
        Employee employee3 = employeeRepository.save(new Employee("c", 18, "Male", 120));
        Employee employee4 = employeeRepository.save(new Employee("d", 18, "Male", 120));
        companyRepository.save(new Company("ABC", Collections.singletonList(employee1.getId())));
        companyRepository.save(new Company("DEF", Collections.singletonList(employee2.getId())));
        Company company3 = new Company("GHI", Collections.singletonList(employee3.getId()));
        companyRepository.save(company3);
        Company company4 = new Company("JKL", Collections.singletonList(employee4.getId()));
        companyRepository.save(company4);
        //when
        //then
        mockMvc.perform(get("/companies").param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company3.getId()))
                .andExpect(jsonPath("$[0].name").value("GHI"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees[0].id").value(employee3.getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value("c"))
                .andExpect(jsonPath("$[0].employees[0].age").value(18))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(120))
                .andExpect(jsonPath("$[1].id").value(company4.getId()))
                .andExpect(jsonPath("$[1].name").value("JKL"))
                .andExpect(jsonPath("$[1].employeesNumber").value(1))
                .andExpect(jsonPath("$[1].employees[0].id").value(employee4.getId()))
                .andExpect(jsonPath("$[1].employees[0].name").value("d"))
                .andExpect(jsonPath("$[1].employees[0].age").value(18))
                .andExpect(jsonPath("$[1].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[1].employees[0].salary").value(120));
    }


}
