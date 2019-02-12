package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.model.Student;
import com.spring.repository.Repository;

@Service
public class StudentService {

	@Autowired
	private Repository repository;

	public boolean doesExist(Student student) {
		return findById(student.getStudentId()) != null;
	}

	public Student findById(int studentId) {
		return repository.findByStudentId(studentId);
	}

	public void saveStudent(Student student) {
		repository.save(student);
	}

	public List<Student> findAllStudents() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public void updateStudent(Student currentStudent) {
		saveStudent(currentStudent);
	}

	public void deleteStudentById(int id) {
		repository.deleteById(id);
	}

	public void deleteAllProducts() {
		repository.deleteAll();
	}


}
