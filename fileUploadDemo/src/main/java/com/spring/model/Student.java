package com.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="students")
public class Student {

	@Id
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="school_yr")
	private short schoolYr;
	
	@Column(name = "campus")
	private byte campus;
	
	@Column(name="grade_level")
	private short gradeLevel;
	
	@Column(name="name")
	private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="entry_date")
	private Date entryDate;
	

	public Student() {
		
	}
	
	public Student(int studentId, short schoolYr, byte campus, short gradeLevel, String name, Date entryDate) {
		this.studentId = studentId;
		this.schoolYr = schoolYr;
		this.campus = campus;
		this.gradeLevel = gradeLevel;
		this.name = name;
		this.entryDate = entryDate;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public short getSchoolYr() {
		return schoolYr;
	}

	public void setSchoolYr(short schoolYr) {
		this.schoolYr = schoolYr;
	}

	public byte getCampus() {
		return campus;
	}

	public void setCampus(byte campus) {
		this.campus = campus;
	}

	public short getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(short gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", schoolYr=" + schoolYr + ", campus=" + campus + ", gradeLevel="
				+ gradeLevel + ", name=" + name + ", entryDate=" + entryDate + "]";
	}
	
	
	
	

}
