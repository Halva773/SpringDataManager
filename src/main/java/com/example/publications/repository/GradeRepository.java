package com.example.publications.repository;

import com.example.publications.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    
    List<Grade> findByStudentNameContainingIgnoreCase(String studentName);
    
    List<Grade> findBySubjectContainingIgnoreCase(String subject);
    
    List<Grade> findByGrade(Integer grade);
}

