package org.example;

public class Student {
    String name, regNumber, gender, course, subject; // Fix order

    public Student(String name, String regNumber, String gender, String course, String subject) {
        this.name = name;
        this.regNumber = regNumber;
        this.gender = gender;
        this.course = course;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Full Name: " + name + ", Registration Number: " + regNumber +
                ", Gender: " + gender + ", Course: " + course + ", Subject: " + subject;
    }
}
