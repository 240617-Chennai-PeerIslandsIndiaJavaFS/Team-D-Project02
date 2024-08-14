package com.teamD.RevTaskManagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageNotFoundException extends RuntimeException{
    private String message;
}
