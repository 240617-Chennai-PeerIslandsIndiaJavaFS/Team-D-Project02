package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDAO extends JpaRepository<Profile,Long> {
    Profile findByEmail(String email);
}
