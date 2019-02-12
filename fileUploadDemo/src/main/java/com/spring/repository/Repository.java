package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Student;

public interface Repository extends JpaRepository<Student, Integer> {

	Student findByStudentId(int studentId);

	

}
