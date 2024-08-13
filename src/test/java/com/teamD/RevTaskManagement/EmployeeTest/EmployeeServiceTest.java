package com.teamD.RevTaskManagement.EmployeeTest;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.exceptions.InvalidCredentialsException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.service.EmployeeService;
import com.teamD.RevTaskManagement.utilities.EmailService;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import com.teamD.RevTaskManagement.utilities.PasswordEncrypter;
import com.teamD.RevTaskManagement.utilities.RandomCredentialsGenerator;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private EmailService emailService;

    @Mock
    private RandomCredentialsGenerator generator;

    @Mock
    private ModelUpdater modelUpdater;

    @Mock
    private PasswordEncrypter passwordEncrypter;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee1 = new Employee();
        employee1.setEmail("employee1@example.com");
        employee1.setEmployeeName("Employee One");
        employee1.setPassword("hashedPassword1");

        employee2 = new Employee();
        employee2.setEmail("employee2@example.com");
        employee2.setEmployeeName("Employee Two");
        employee2.setPassword("hashedPassword2");

        employeeList = Arrays.asList(employee1, employee2);
    }

    @Test
    void testCreateEmployee_Success() throws MessagingException {
        when(employeeDAO.findByEmail(employee1.getEmail())).thenReturn(null);
        when(generator.generateRandomPassword()).thenReturn("generatedPassword");
        when(passwordEncrypter.hashPassword("generatedPassword")).thenReturn("hashedPassword");
        doNothing().when(emailService).sendMail(anyString(), anyString(), anyString());
        when(employeeDAO.save(employee1)).thenReturn(employee1);

        Employee createdEmployee = employeeService.createEmployee(employee1);

        assertNotNull(createdEmployee);
        assertEquals(employee1.getEmail(), createdEmployee.getEmail());
        verify(employeeDAO).save(employee1);
    }

    @Test
    void testCreateEmployee_EmailAlreadyExists() {
        when(employeeDAO.findByEmail(employee1.getEmail())).thenReturn(employee1);

        InvalidCredentialsException thrown = assertThrows(
                InvalidCredentialsException.class,
                () -> employeeService.createEmployee(employee1),
                "Expected createEmployee() to throw InvalidCredentialsException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("email already exist"));
    }


    @Test
    void testLoginEmployee_InvalidEmail() {
        when(employeeDAO.findByEmail(employee1.getEmail())).thenReturn(null);

        InvalidCredentialsException thrown = assertThrows(
                InvalidCredentialsException.class,
                () -> employeeService.loginEmployee(employee1.getEmail(), "password"),
                "Expected loginEmployee() to throw InvalidCredentialsException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Invalid email"));
    }

    @Test
    void testLoginEmployee_InvalidPassword() {
        when(employeeDAO.findByEmail(employee1.getEmail())).thenReturn(employee1);
        when(passwordEncrypter.hashPassword("wrongPassword")).thenReturn("wrongHashedPassword");

        InvalidCredentialsException thrown = assertThrows(
                InvalidCredentialsException.class,
                () -> employeeService.loginEmployee(employee1.getEmail(), "wrongPassword"),
                "Expected loginEmployee() to throw InvalidCredentialsException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Invalid password"));
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmail(employee1.getEmail());
        updatedEmployee.setEmployeeName("Updated Name");

        when(employeeDAO.findById(1L)).thenReturn(Optional.of(employee1));
        when(modelUpdater.updateFields(employee1, updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeDAO.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertNotNull(result);
        assertEquals("Updated Name", result.getEmployeeName());
        verify(employeeDAO).findById(1L);
        verify(employeeDAO).save(updatedEmployee);
    }


    @Test
    void testFetchAllEmployees() {
        when(employeeDAO.findAll()).thenReturn(employeeList);

        List<Employee> employees = employeeService.fetchAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(employee1.getEmail(), employees.get(0).getEmail());
        assertEquals(employee2.getEmail(), employees.get(1).getEmail());
    }

    


}
