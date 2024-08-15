package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {
    List<Message> findBySender(Employee sender);
    List<Message> findByReceiver(Employee receiver);
}
