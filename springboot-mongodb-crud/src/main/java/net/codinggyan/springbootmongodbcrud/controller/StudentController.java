package net.codinggyan.springbootmongodbcrud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.codinggyan.springbootmongodbcrud.document.Student;
import net.codinggyan.springbootmongodbcrud.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.codinggyan.springbootmongodbcrud.repository.StudentRepository;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/Students")
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    @PostMapping("/Students")
    public Student createStudent(@RequestBody Student Student) {

        Random random = new Random();
        Student.setId((Student.getFirstName() + Student.getLastName() + Student.getEmailId()) + random.nextInt(1000));
        return studentRepository.save(Student);
    }


    @GetMapping("/Students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {

        Student Student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));
        return ResponseEntity.ok(Student);
    }


    @PutMapping("/Students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student StudentDetails) {

        Student Student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));

        Student.setFirstName(StudentDetails.getFirstName());
        Student.setLastName(StudentDetails.getLastName());
        Student.setEmailId(StudentDetails.getEmailId());
        Student updatedStudent = studentRepository.save(Student);
        return ResponseEntity.ok(updatedStudent);
    }


    @DeleteMapping("/Students/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable String id) {

        Student Student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));

        studentRepository.delete(Student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
