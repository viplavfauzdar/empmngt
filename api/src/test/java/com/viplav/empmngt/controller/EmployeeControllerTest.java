package com.viplav.empmngt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viplav.empmngt.model.Employee;
import com.viplav.empmngt.model.EmployeeRepository;
import org.json.JSONArray;
import org.json.JSONString;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    //not need for this test
   /* @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }*/

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("viplav");
        employee.setEmailId("v@v.v");
        employeeList.add(employee);

        //What is this doing?
        when(employeeRepository.findAll()).thenReturn(employeeList);

        mockMvc.perform(get("/v1/api/employees"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void createEmployee() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("viplav");
        employee.setEmailId("v@v.v");

        mockMvc.perform(post("/v1/api/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}