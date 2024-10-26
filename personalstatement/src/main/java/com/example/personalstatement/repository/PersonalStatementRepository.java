package com.example.personalstatement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.personalstatement.entity.PersonalStatement;
import com.example.personalstatement.entity.User;

import java.util.List;

public interface PersonalStatementRepository extends JpaRepository<PersonalStatement, Long> {
    List<PersonalStatement> findByUser(User user);
}

