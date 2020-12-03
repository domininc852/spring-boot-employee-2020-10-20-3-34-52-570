package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repositories.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;

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
        mockMvc.perform(get("/employees")).
                andExpect(status().isOk())
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
                "            \"age\": 20,\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"salary\": 120\n" +
                "    }";
        //when

        //then
        mockMvc.perform(put("/employees/" + employee.getId()).contentType(MediaType.APPLICATION_JSON).content(employeeToString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("bar"))
                .andExpect(jsonPath("$.age").value(20))
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
        ObjectId fakeID= new ObjectId();
        //when

        //then
        mockMvc.perform(delete("/employees/"+fakeID))
                .andExpect(status().isNotFound());
    }
    @Test
    public void should_return_employee_when_get_employee_by_id_given_an_employeeID() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("bar", 20, "Female", 120));
        //when

        //then
        mockMvc.perform(get("/employees/"+employee.getId()))
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
        mockMvc.perform(get("/employees/"+fakeID))
                .andExpect(status().isNotFound());
    }

}
