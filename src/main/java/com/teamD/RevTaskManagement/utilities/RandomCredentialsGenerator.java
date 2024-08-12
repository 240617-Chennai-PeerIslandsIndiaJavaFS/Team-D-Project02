package com.teamD.RevTaskManagement.utilities;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class RandomCredentialsGenerator {

    public String generateOTP(){
        Random random=new Random();
        int otp=new Random().nextInt(900000)+100000;
        return String.valueOf(otp);
    }

    public String generateRandomPassword(){
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = upperCaseLetters.toLowerCase();
        String numbers = "0123456789";
        String allChars = upperCaseLetters + lowerCaseLetters + numbers;
        SecureRandom random=new SecureRandom();
        StringBuilder password = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        return password.toString();
    }
}
