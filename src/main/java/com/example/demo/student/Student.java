package com.example.demo.student;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(
                        name = "student_sequence",
                        sequenceName = "student_sequence",
                        allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private LocalDate dob;
    @Getter @Setter
    private String email;

    @Transient
    private Integer age;

    public Student() {
    }

    public Student(Long id, String name,  LocalDate dob, String email) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Student(String name,  LocalDate dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }

    public Integer getAge(){
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

}
