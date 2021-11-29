package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService studentService;


    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
       // autoCloseable.close();
    }

    @Test
    void canGetStudents() {
        //when
        studentService.getStudents();
        //then
        verify(studentRepository).findAll();

    }

    @Test
    @Disabled
    void getStudent() {
    }

    @Test
    @Disabled
    void getStudentByEmail() {
    }

    @Test
    void canAddNewStudent() {
        //given
        Student student = new Student("Jamila", LocalDate.of(2001,01,01),"jmonk@test.com");

        //when
        studentService.addNewStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capture = studentArgumentCaptor.getValue();
        assertThat(capture).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        Student student = new Student("Jamila", LocalDate.of(2001,01,01),"jmonk@test.com");
        Optional<Student> studentOptional = Optional.of(student);
        //when
        given(studentRepository
                .findStudentByEmail(student.getEmail())).willReturn(studentOptional);

        //then
        assertThatThrownBy(()->studentService.addNewStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email taken: %s".formatted(student.getEmail()));

        verify(studentRepository, never()).save(any());
    }

    @Test
    @Disabled
    void canDeleteStudent() {
        //given
        long studentId = 1l;
        given(studentRepository.existsById(studentId)).willReturn(true);

        //when
        studentService.deleteStudent(studentId);
        //then
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    @Disabled
    void updateStudent() {
    }
}