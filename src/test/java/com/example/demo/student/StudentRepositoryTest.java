package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;


    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldFindStudentByEmail() {

        //given
        String email = "jmonk@tet.com";
        Student student = new Student("Jamila", LocalDate.of(2001,01,01),email);
        studentRepository.save(student);
        //when
        Optional<Student> studentOptional= studentRepository.findStudentByEmail(email);

        //then
        assertThat(studentOptional.isPresent()).isTrue();

    }

    @Test
    void itShouldNotFindStudentByEmail() {

        //given
        String email = "jmonk@tet.com";

        //when
        Optional<Student> studentOptional= studentRepository.findStudentByEmail(email);

        //then
        assertThat(studentOptional.isPresent()).isFalse();

    }


}