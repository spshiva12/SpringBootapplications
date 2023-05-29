package com.example.restapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restapi.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
