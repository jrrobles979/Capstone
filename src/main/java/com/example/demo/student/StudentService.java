package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private Logger logger = LoggerFactory.getLogger(StudentRepository.class);


    @Autowired
    public StudentService(StudentRepository repository){
        studentRepository=repository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(long studentId){
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(studentOptional.isEmpty()){
            logger.warn("getStudent | Student not found. id: %s".formatted(studentId));
            throw new StudentNotFoundException("Student not found. id: %s".formatted(studentId));
        }
        logger.info("getStudent | Student found: %s".formatted(studentOptional.get()));
        return studentOptional.get();
    }

    public Student getStudentByEmail(String email){
        Optional<Student> studentOptional= studentRepository.findStudentByEmail(email);
        if(studentOptional.isEmpty()){
            logger.warn("getStudentByEmail | Student not found. email: %s".formatted(email));
            throw new StudentNotFoundException("Student not found. email: %s".formatted(email));
        }
        return studentOptional.get();
    }

    public void addNewStudent(Student student){
        logger.info("Saving new student: %s".formatted(student));
        Optional<Student> studentOptional= studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            logger.warn("Email taken: %s".formatted(student.getEmail()));
            throw new BadRequestException("Email taken: %s".formatted(student.getEmail()) );
        }
        studentRepository.save(student);
        logger.info("Student saved!");
    }

    public void deleteStudent(long studentId){
        if(!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException("Student does not exist. id: %s".formatted(studentId));
        }
        studentRepository.deleteById(studentId);
    }

    @Transient
    public void updateStudent(Long stundentId, String name, String email){

        Student student= studentRepository.findById(stundentId).orElseThrow(
                () ->  new StudentNotFoundException("Student does not exist. Id: %s".formatted(stundentId))
        );

        if(name!=null && name.length()>0 && !Objects.equals(name, student.getName()) ){
                student.setName(name);
        }

        if(email!=null && email.length()>0 && !Objects.equals(email,student.getEmail())){
            studentRepository.findStudentByEmail(email).orElseThrow(
                    ()->   new BadRequestException("Email taken: %s".formatted(email))
            );
            student.setEmail(email);
        }

        studentRepository.save(student);

    }








}
