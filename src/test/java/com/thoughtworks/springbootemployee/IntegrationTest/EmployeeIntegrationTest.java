package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.entities.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_all_employees_when_get_all_given_employees() throws Exception {

        //given
        Employee employee = new Employee("bar", 20, "Female", 120);
        employeeRepository.save(employee);
        //when
        //then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("bar"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(120));
    }

    @Test
    public void should_return_employee_when_create_given_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "        \"name\": \"foobar\",\n" +
                "        \"age\": 3,\n" +
                "        \"gender\": null,\n" +
                "        \"salary\": 4\n" +
                "    }";
        //when
        //then
        mockMvc
                .perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeAsJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("foobar"))
                .andExpect(jsonPath("$.age").value(3))
                .andExpect(jsonPath("$.salary").value(4));

    }

    @Test
    public void should_return_updated_employee_when_update_employee_given_an_employee() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        String employeeToString = " {\n" +
                "            \"name\": \"bar\",\n" +
                "            \"age\": 30,\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"salary\": 120\n" +
                "    }";
        //when

        //then
        mockMvc.perform(put("/employees/" + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeToString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("bar"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(120))
                .andExpect(jsonPath("$.gender").value("Female"));
    }

    @Test
    public void should_return_404_error_when_update_employee_given_an_invalid_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        String employeeToString = " {\n" +
                "            \"name\": \"bar\",\n" +
                "            \"age\": 20,\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"salary\": 120\n" +
                "    }";
        ObjectId fakeID = new ObjectId();
        //when

        //then
        mockMvc.perform(put("/employees/" + fakeID).contentType(MediaType.APPLICATION_JSON).content(employeeToString))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_delete_a_employee_when_delete_employee_given_an_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));

        //when

        //then
        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isOk());
        assertTrue(employeeRepository.findById(employee.getId()).isEmpty());
    }

    @Test
    public void should_return_404_error__when_delete_employee_given_an_invalid_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        ObjectId fakeID = new ObjectId();
        //when

        //then
        mockMvc.perform(delete("/employees/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_employee_when_get_employee_by_id_given_an_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        //when

        //then
        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("bar"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(120));
    }

    @Test
    public void should_return_404_error_when_get_employee_by_id_given_an_invalid_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        ObjectId fakeID = new ObjectId();
        //when

        //then
        mockMvc.perform(get("/employees/" + fakeID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_employees_with_same_gender_when_get_employee_by_gender_given_gender() throws Exception {
        //given
        employeeRepository.save(new Employee("bar", 20, "Female", 120));
        Employee employee = employeeRepository.save(new Employee("dominic", 30, "Male", 10));
        //when

        //then
        mockMvc.perform(get("/employees").param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee.getId()))
                .andExpect(jsonPath("$[0].name").value("dominic"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(10));

    }

    @Test
    public void should_return_paged_employees_when_get_employee_by_page_and_page_size_given_page_and_page_size() throws Exception {
        //given
        employeeRepository.save(new Employee("bar", 20, "Female", 120));
        employeeRepository.save(new Employee("dominic", 30, "Male", 10));
        Employee employee1 = employeeRepository.save(new Employee("jeany", 18, "Female", 1200000));
        Employee employee2 = employeeRepository.save(new Employee("Woody", 18, "Male", 120000));
        //when

        //then
        mockMvc.perform(get("/employees").param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].name").value("jeany"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(1200000))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(jsonPath("$[1].name").value("Woody"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(120000));

    }

}
