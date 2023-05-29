package com.example.restapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restapi.entity.Student;
import com.example.restapi.exception.StudentNotFoundException;
import com.example.restapi.repo.StudentRepository;

@RestController
public class StudentResource {

	@Autowired
	private StudentRepository studRepo;

	@GetMapping("/student")
	public List<Student> retrieveAllStudents() {
		return studRepo.findAll();
	}

	@GetMapping("/student/{sid}")
	public Student retiriveStudent(@PathVariable Long sid) {
		Optional<Student> student = studRepo.findById(sid);

		if (student.empty() != null)
			throw new StudentNotFoundException("Studentid-" + sid);

		return student.get();
	}

	@DeleteMapping("/student/{sid}")
	public void deleteStudent(@PathVariable Long sid) {

		studRepo.deleteById(sid);

	}

	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedstudent = studRepo.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/sid")
				.buildAndExpand(savedstudent.getSid()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/students/{sid}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long sid) {
		Optional<Student> studentOptional = studRepo.findById(sid);

		if (studentOptional.empty() != null)
			return ResponseEntity.notFound().build();

		student.setSid(sid);

		studRepo.save(student);

		return ResponseEntity.noContent().build();
	}

}
