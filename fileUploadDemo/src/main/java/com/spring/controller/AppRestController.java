package com.spring.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.model.Student;
import com.spring.service.StudentService;
import com.spring.util.Error;

@RestController
public class AppRestController {
	
    private static final Logger LOGGER = LogManager.getLogger(AppRestController.class);

	@Autowired
	private StudentService studentService;
	
	// Retrieve CSV file 
	@RequestMapping(value="/students", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		File csvfile = new File("C:\\Users\\Mayank\\Documents\\workspace\\fileUploadDemo\\uploads\\"+file.getOriginalFilename());
		csvfile.createNewFile();
		FileOutputStream fout = new FileOutputStream(csvfile);
		fout.write(file.getBytes());
		fout.close();
		saveStudentData(file.getOriginalFilename());
		return new ResponseEntity<>("Data is Successfully updated", HttpStatus.OK);
	}

	// Read CSV file and add to database
	public void saveStudentData(String fileName) {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("C:\\Users\\Mayank\\Documents\\workspace\\fileUploadDemo\\uploads\\" + fileName));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				
					Date date;
					try {
						date = formatter.parse(data[3]);
						System.out.println("Date is: " + date);
						Student student = new Student();
						student.setSchoolYr(Short.parseShort(data[0]));
						student.setCampus(Byte.parseByte(data[1]));
						student.setStudentId(Integer.parseInt(data[2]));
						student.setEntryDate(date);
						student.setGradeLevel(Short.parseShort(data[4]));
						student.setName(data[5]);
						studentService.saveStudent(student);
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
	// Retrieves all Students
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> listAllProducts() {
		List<Student> students = studentService.findAllStudents();
		if (students.isEmpty()) {
			return new ResponseEntity<>(students, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	// Retrieve a Single Student
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
		try {
			Student student = studentService.findById(id);
			if (student == null) {
				return new ResponseEntity<>(new Error("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Exception thrown by API: ", e);
		}

		return new ResponseEntity<>(new Error("Error Thrown By API"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	// Add Student
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
		if (studentService.doesExist(student)) {
			return new ResponseEntity<>(
					new Error("Unable to create. A Student with name " + student.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		studentService.saveStudent(student);

		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	
    // Update a Student
    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        Student currentStudent = studentService.findById(id);

        if (currentStudent == null) {
            return new ResponseEntity<>(new Error("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentStudent.setName(student.getName());
        currentStudent.setCampus(student.getCampus());
        currentStudent.setEntryDate(student.getEntryDate());
        currentStudent.setGradeLevel(student.getGradeLevel());
        currentStudent.setSchoolYr(student.getSchoolYr());
        studentService.updateStudent(currentStudent);
        return new ResponseEntity<>(currentStudent, HttpStatus.OK);
    }
    
    // Delete a Student
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(new Error("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudentById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    // Delete All Students
    @RequestMapping(value = "/students", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteAllProducts() {
        studentService.deleteAllProducts();
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

}
