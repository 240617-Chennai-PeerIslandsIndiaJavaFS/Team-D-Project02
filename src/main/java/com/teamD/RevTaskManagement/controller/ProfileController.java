package com.teamD.RevTaskManagement.controller;

import com.teamD.RevTaskManagement.model.Profile;
import com.teamD.RevTaskManagement.service.ProfileService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PostMapping
    public ResponseEntity<byte[]> setImage(@RequestParam String email, @RequestParam MultipartFile file){
        Profile profile=profileService.setProfile(email,file);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(profile.getImage(),headers,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<byte[]> fetchProfile(@RequestParam String email){
        Profile profile=profileService.fetchProfileByEmail(email);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(profile.getImage(),headers,HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<byte[]> deleteProfile(@RequestParam String email){
        Profile profile=profileService.deleteProfile(email);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(profile.getImage(),headers,HttpStatus.ACCEPTED);
    }
}
