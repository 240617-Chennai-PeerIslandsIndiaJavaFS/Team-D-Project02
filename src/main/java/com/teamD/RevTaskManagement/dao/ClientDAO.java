package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client,Long> {
}
