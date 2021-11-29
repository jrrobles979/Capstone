package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> list(){
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Long id){
        return studentService.getStudent(id);
    }


    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent( @PathVariable("studentId") Long id,
                               @PathVariable(required = false) String name,
                               @PathVariable(required = false) String email){
        studentService.updateStudent(id, name, email );
    }

    @DeleteMapping(path = "{studentId}")
    private void deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudent(id);
    }


}
