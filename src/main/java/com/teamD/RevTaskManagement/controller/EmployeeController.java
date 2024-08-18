package com.teamD.RevTaskManagement.controller;

import ch.qos.logback.core.model.conditional.ElseModel;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.service.EmployeeService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Employee>> registerEmployee(@RequestBody Employee employee){
            BaseResponse<Employee> baseResponse=new BaseResponse<>();
            baseResponse.setMessages("Registration Successfull");
            baseResponse.setData(employeeService.createEmployee(employee));
            baseResponse.setStatus(HttpStatus.CREATED.value());
            return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<BaseResponse<Employee>> loginEmployee(@RequestParam String email,@RequestParam String password){
        BaseResponse<Employee> baseResponse=new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.ACCEPTED.value());
        baseResponse.setMessages("Login Successfull");
        baseResponse.setData(employeeService.loginEmployee(email,password));
        return new ResponseEntity<>(baseResponse,HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Employee>> updateEmployee(@RequestBody Employee employee){
        BaseResponse<Employee> baseResponse=new BaseResponse<>();
        baseResponse.setData(employeeService.updateEmployee(employee.getEmployeeId(),employee));
        baseResponse.setMessages("Updated successfull");
        baseResponse.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Employee>>> fetchAllEmployees(){
        BaseResponse<List<Employee>> baseResponse=new BaseResponse<>();
        baseResponse.setData(employeeService.fetchAllEmployees());
        baseResponse.setMessages("Fetched all users");
        baseResponse.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping("/otp")
    public  ResponseEntity<BaseResponse<String>> generateOTP(String email){
        BaseResponse<String> baseResponse=new BaseResponse<>();
        baseResponse.setData(employeeService.GenerateOtp(email));
        baseResponse.setStatus(HttpStatus.CREATED.value());
        baseResponse.setMessages("OTP generated");
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @GetMapping("/name")
    public ResponseEntity<BaseResponse<Employee>> findByName(@RequestParam String name){
        BaseResponse<Employee> baseResponse=new BaseResponse<>("Fetched employee",employeeService.fetchByName(name),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
    @GetMapping("/email")
    public ResponseEntity<BaseResponse<Employee>> findByEmail(@RequestParam String email) {
        BaseResponse<Employee> baseResponse = new BaseResponse<>("Fetched employee", employeeService.fetchByEmail(email), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/reset")
    public ResponseEntity<BaseResponse<Employee>> resetPassword(@RequestParam String email,@RequestParam String password){
        BaseResponse<Employee> baseResponse = new BaseResponse<>("updated employee", employeeService.updateEmployeePassword(email,password), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


}
