package com.teamD.RevTaskManagement.EmployeeTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.EmployeeController;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.service.EmployeeService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() throws ParseException {
        employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmail("employee1@example.com");
        employee1.setEmployeeName("Employee One");
        employee1.setPassword("hashedPassword");

        employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmail("employee2@example.com");
        employee2.setEmployeeName("Employee Two");
        employee2.setPassword("hashedPassword");
    }

    @Test
    void testRegisterEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value("employee1@example.com"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.messages").value("Registration Successfull"));
    }

    @Test
    void testLoginEmployee() throws Exception {
        when(employeeService.loginEmployee(anyString(), anyString())).thenReturn(employee1);

        mockMvc.perform(get("/api/employee/login")
                        .param("email", "employee1@example.com")
                        .param("password", "hashedPassword"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.email").value("employee1@example.com"))
                .andExpect(jsonPath("$.status").value(HttpStatus.ACCEPTED.value()))
                .andExpect(jsonPath("$.messages").value("Login Successfull"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("employee1@example.com"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("Updated successfull"));
    }

    @Test
    void testFetchAllEmployees() throws Exception {
        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        when(employeeService.fetchAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("Fetched all users"));
    }

    @Test
    void testGenerateOtp() throws Exception {
        when(employeeService.GenerateOtp(anyString())).thenReturn("123456");

        mockMvc.perform(get("/api/employee/otp")
                        .param("email", "employee1@example.com"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("123456"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.messages").value("OTP generated"));
    }
}
