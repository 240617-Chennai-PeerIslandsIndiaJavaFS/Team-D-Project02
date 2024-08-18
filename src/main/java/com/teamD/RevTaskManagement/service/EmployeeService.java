package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.enums.EmployeeStatus;
import com.teamD.RevTaskManagement.exceptions.InvalidCredentialsException;
import com.teamD.RevTaskManagement.exceptions.InvalidEmailException;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Skill;
import com.teamD.RevTaskManagement.utilities.EmailService;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import com.teamD.RevTaskManagement.utilities.PasswordEncrypter;
import com.teamD.RevTaskManagement.utilities.RandomCredentialsGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    SkillService skillService;

    @Autowired
    RandomCredentialsGenerator generator;

    @Autowired
    ModelUpdater modelUpdater;

    @Autowired
    PasswordEncrypter passwordEncrypter;

    public Employee createEmployee(Employee employee){
        Employee dbEmployee=employeeDAO.findByEmail(employee.getEmail());
        if(dbEmployee==null){

            List<Skill> managedSkills = employee.getSkills().stream()
                    .map(skill -> skillService.getSkillByName(skill.getSkill()))
                    .collect(Collectors.toList());

            employee.setSkills(managedSkills);

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
            if(dbEmployee.getStatus()== EmployeeStatus.ACTIVE) {
                dbEmployee.setPassword(null);
                return dbEmployee;
            }
            throw new InvalidCredentialsException("Not allowed to login");
        }
        throw  new InvalidCredentialsException("Invalid password");
    }

    public  Employee updateEmployee(long id, Employee employee){
        Employee dbEmployee=employeeDAO.findById(id).get();
        if(employee==null){
            throw new InvalidCredentialsException("User not found");
        }
        List<Skill> existingSkills = skillService.getAllSkills();

        // Map incoming skills to existing skills
        List<Skill> updatedSkills = employee.getSkills().stream()
                .map(skill -> {
                    Skill existingSkill = existingSkills.stream()
                            .filter(s -> s.getSkill().equals(skill.getSkill()))
                            .findFirst()
                            .orElse(null);
                    return existingSkill != null ? existingSkill : skill;
                })
                .collect(Collectors.toList());

        employee.setSkills(updatedSkills);

        return employeeDAO.save(modelUpdater.updateFields(dbEmployee,employee));
    }

    public List<Employee> fetchAllEmployees(){
        return employeeDAO.findAll();
    }

    public String GenerateOtp(String email) {
        Employee employee=employeeDAO.findByEmail(email);
        if(employee==null){
            throw new InvalidCredentialsException("Email does not exist");
        }
        String OTP= generator.generateOTP();
        try {
            emailService.sendMail(employee.getEmail(), "Password Reset", emailService.passwordResetTemplate(employee.getEmployeeName(), OTP));
        }
        catch (MessagingException e){
            throw new InvalidEmailException("Not a valid mail");
        }
        return OTP;
    }

    public Employee fetchByName(String name){
        Employee dbemployee=employeeDAO.findByEmployeeName(name);
        if(dbemployee==null){
            throw new NotFoundException("Employee with name: "+name+" not found");
        }
        return dbemployee;

    }
    public Employee fetchByEmail(String email) {
        Employee dbEmployee = employeeDAO.findByEmail(email);
        if (dbEmployee == null) {
            throw new NotFoundException("Employee with email: " + email + " not found");
        }
        return dbEmployee;
    }

    public Employee updateEmployeePassword(String email,String password){
        Employee dbEmployee=employeeDAO.findByEmail(email);
        if(dbEmployee==null){
            throw  new NotFoundException("No user with the mail");
        }
        dbEmployee.setPassword(passwordEncrypter.hashPassword(password));
        return employeeDAO.save(dbEmployee);
    }



}
