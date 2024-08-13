package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.exceptions.InvalidCredentialsException;
import com.teamD.RevTaskManagement.exceptions.InvalidEmailException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.utilities.EmailService;
import com.teamD.RevTaskManagement.utilities.PasswordEncrypter;
import com.teamD.RevTaskManagement.utilities.RandomCredentialsGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    RandomCredentialsGenerator generator;

    @Autowired
    PasswordEncrypter passwordEncrypter;

    public Employee createEmployee(Employee employee){
        Employee dbEmployee=employeeDAO.findByEmail(employee.getEmail());
        if(dbEmployee==null){
            String password=generator.generateRandomPassword();

           try {
               emailService.sendMail(employee.getEmail(), "Rev Task Registration", emailService.registrationTemplate(employee.getEmployeeName(),employee.getEmail(),password));
               employee.setPassword(passwordEncrypter.hashPassword(password));
               dbEmployee=employeeDAO.save(employee);
               return dbEmployee;
           }
           catch (MessagingException ex){
                throw new InvalidEmailException("Invalid mail");
           }

        }
        throw new InvalidCredentialsException("email already exist");
    }

    public Employee loginEmployee(String email,String password){
        Employee dbEmployee=employeeDAO.findByEmail(email);
        if(dbEmployee==null){
        throw new InvalidCredentialsException("Invalid email");
        }
        password=passwordEncrypter.hashPassword(password);
        if(password.equals(dbEmployee.getPassword())){
            dbEmployee.setPassword(null);
            return dbEmployee;
        }
        throw  new InvalidCredentialsException("Invalid password");
    }

    public  Employee updateEmployee(long id, Employee employee){
        Employee dbEmployee=employeeDAO.findById(id).get();
        if(employee==null){
            throw new InvalidCredentialsException("User not found");
        }
        return null;

    }

    
}
