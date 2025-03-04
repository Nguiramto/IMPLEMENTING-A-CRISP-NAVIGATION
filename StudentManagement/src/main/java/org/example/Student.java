package org.example;

import java.sql.Date;

public class Student {
    public String regNumber;
    String name;
    private final String regNo;
    String course;
    String subject;
    String gender;
    private final java.sql.Date dob;

    public Student(String name, String regNo, String course, String subject, String gender, java.sql.Date dob) {
        this.name = name;
        this.regNo = regNo;
        this.course = course;
        this.subject = subject;
        this.gender = gender;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getCourse() {
        return course;
    }

    public String getSubject() {
        return subject;
    }

    public String getGender() {
        return gender;
    }

    public java.sql.Date getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Reg No: " + regNo + ", Course: " + course + ", Subject: " + subject + ", Gender: " + gender + ", DOB: " + dob;
    }
}
