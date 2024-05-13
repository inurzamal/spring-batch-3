package com.springbatch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "percentage_of_marks")
    private double percentageOfMarks;

    @Embedded
    private Address address;

    // Constructors
    public Student() {
        // Default constructor required by JPA
    }

    public Student(String studentName, String degree, String specialization, double percentageOfMarks, Address address) {
        this.studentName = studentName;
        this.degree = degree;
        this.specialization = specialization;
        this.percentageOfMarks = percentageOfMarks;
        this.address = address;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getPercentageOfMarks() {
        return percentageOfMarks;
    }

    public void setPercentageOfMarks(double percentageOfMarks) {
        this.percentageOfMarks = percentageOfMarks;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", degree='" + degree + '\'' +
                ", specialization='" + specialization + '\'' +
                ", percentageOfMarks=" + percentageOfMarks +
                ", address=" + address +
                '}';
    }
}
