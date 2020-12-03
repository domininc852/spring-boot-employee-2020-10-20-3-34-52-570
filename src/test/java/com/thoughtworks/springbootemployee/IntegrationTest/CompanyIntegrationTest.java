package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.CompanyRepository;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeRepository.save(new Employee("bar", 20, "Female", 120)));
        Company company = new Company("ABC", 1, employees);
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
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        String companyAsJson = "{\n" +
                "    \"name\": \"ABC\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [\n" +
                "     " + employee.toJSON() + "\n" +
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
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Company company = new Company("ABC", 1, employees);
        companyRepository.save(company);

        String companyAsJson = "{\n" +
                "    \"name\": \"ABCD\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [\n" +
                "     " + employee.toJSON() + "\n" +
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
    public void should_return_404_error_when_update_given_invalid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        companyRepository.save(new Company("ABC", 1, employees));
        String companyAsJson = "{\n" +
                "    \"name\": \"ABCD\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [\n" +
                "     " + employee.toJSON() + "\n" +
                "    ]\n" +
                "}";
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(put("/companies/" + fakeID).contentType(MediaType.APPLICATION_JSON).content(companyAsJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void should_delete_company_when_delete_given_valid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Company company = new Company("ABC", 1, employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isOk());
        assertTrue(companyRepository.findById(company.getId()).isEmpty());
    }

    @Test
    public void should_return_404_error_when_delete_given_invalid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        companyRepository.save(new Company("ABC", 1, employees));
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(delete("/companies/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_company_when_get_company_by_id_given_valid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Company company = new Company("ABC", 1, employees);
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
    public void should_return_404_error_when_get_company_by_id_given_invalid_companyID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        companyRepository.save(new Company("ABC", 1, employees));
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(get("/companies/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_all_employees_when_get_employees_by_id_given_valid_companyID() throws Exception {
        //given
        Employee employee1 = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Employee employee2 = employeeRepository.save(new Employee("abcd", 18, "Male", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        Company company = new Company("ABC", 1, employees);
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
    public void should_return_404_error_when_get_employees_by_id_given_invalid_companyID() throws Exception {
        //given
        Employee employee1 = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Employee employee2 = employeeRepository.save(new Employee("abcd", 18, "Male", 120));
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        companyRepository.save(new Company("ABC", 1, employees));
        ObjectId fakeID = new ObjectId();
        //when
        //then
        mockMvc.perform(get("/companies/" + fakeID + "/employees"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_paged_companies_when_get_companies_by_page_and_page_size_given_page_and_page_size() throws Exception {
        //given
        Employee employee1 = employeeRepository.save(new Employee("a", 18, "Female", 120));
        Employee employee2 = employeeRepository.save(new Employee("b", 18, "Male", 120));
        Employee employee3 = employeeRepository.save(new Employee("c", 18, "Male", 120));
        Employee employee4 = employeeRepository.save(new Employee("d", 18, "Male", 120));
        List<Employee> employees1 = new ArrayList<>();
        List<Employee> employees2 = new ArrayList<>();
        List<Employee> employees3 = new ArrayList<>();
        List<Employee> employees4 = new ArrayList<>();
        employees1.add(employee1);
        employees2.add(employee2);
        employees3.add(employee3);
        employees4.add(employee4);
        Company company1 = new Company("ABC", 1, employees1);
        companyRepository.save(company1);
        Company company2 = new Company("DEF", 1, employees2);
        companyRepository.save(company2);
        Company company3 = new Company("GHI", 1, employees3);
        companyRepository.save(company3);
        Company company4 = new Company("JKL", 1, employees4);
        companyRepository.save(company4);
        //when
        //then
        mockMvc.perform(get("/companies").param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company3.getId()))
                .andExpect(jsonPath("$[0].name").value("GHI"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees[0].id").value(employees3.get(0).getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value("c"))
                .andExpect(jsonPath("$[0].employees[0].age").value(18))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(120))
                .andExpect(jsonPath("$[1].id").value(company4.getId()))
                .andExpect(jsonPath("$[1].name").value("JKL"))
                .andExpect(jsonPath("$[1].employeesNumber").value(1))
                .andExpect(jsonPath("$[1].employees[0].id").value(employees4.get(0).getId()))
                .andExpect(jsonPath("$[1].employees[0].name").value("d"))
                .andExpect(jsonPath("$[1].employees[0].age").value(18))
                .andExpect(jsonPath("$[1].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[1].employees[0].salary").value(120));
    }


}
