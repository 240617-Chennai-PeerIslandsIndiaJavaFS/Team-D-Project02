package com.teamD.RevTaskManagement.exceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String message){
        super(message);
    }
}
