package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client,Long> {
    Client findByCompanyName(String name);
}