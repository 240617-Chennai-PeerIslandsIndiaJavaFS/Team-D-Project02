package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
}
