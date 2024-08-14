package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.ProfileDAO;
import com.teamD.RevTaskManagement.exceptions.ImageNotFoundException;
import com.teamD.RevTaskManagement.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileService {

    @Autowired
    ProfileDAO profileDAO;

    public Profile setProfile(String email,MultipartFile file){
        Profile dbProfile=profileDAO.findByEmail(email);
        try {
            if (dbProfile == null) {
                Profile profile = new Profile();
                profile.setEmail(email);
                profile.setImage(file.getBytes());
                return profileDAO.save(profile);
            }
            dbProfile.setImage(file.getBytes());
            return profileDAO.save(dbProfile);
        }
        catch (IOException exception){
            throw new ImageNotFoundException("Can't save Image");
        }
    }

    public Profile deleteProfile(String email){
        Profile profile=profileDAO.findByEmail(email);
        if(profile!=null){
            profileDAO.delete(profile);
            return profile;
        }
        throw new ImageNotFoundException("Can't find image with email: "+email);
    }

    public Profile fetchProfileByEmail(String email){
        Profile profile=profileDAO.findByEmail(email);
        if(profile!=null){
            return profile;
        }
        throw new ImageNotFoundException("Can't find image with that email");
    }
}
