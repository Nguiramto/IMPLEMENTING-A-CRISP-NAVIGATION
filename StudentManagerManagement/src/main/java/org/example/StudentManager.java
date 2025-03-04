package org.example;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();
    private int CurrentIndex = -1;

    // Add new student
    public void addStudent(String name, String regNumber, String gender, String course, String subject, Date sqlDate) {
        students.add(new Student(name, regNumber, gender, course, subject, sqlDate));
    }

    // Remove a student
    public void removeStudent() {
        if (CurrentIndex >= 0 && CurrentIndex < students.size()) {
            students.remove(CurrentIndex);
            CurrentIndex = -1;
        }
    }

    // Update student information
    public void updateStudent(String name, String regNumber, String gender, String course, String subject) {
        if (CurrentIndex >= 0 && CurrentIndex < students.size()) {
            students.get(CurrentIndex).name = name;
            students.get(CurrentIndex).regNumber = regNumber;
            students.get(CurrentIndex).course = course;
            students.get(CurrentIndex).subject = subject;
            students.get(CurrentIndex).gender = gender;
        }
    }

    // Get the next student
    public Student getNextStudent() {
        if (students.isEmpty()) return null;
        if (CurrentIndex < students.size() - 1) {
            CurrentIndex++;
        }
        return students.get(CurrentIndex);
    }

    // Get the previous student
    public Student getPreviousStudent() {
        if (students.isEmpty()) return null;
        if (CurrentIndex > 0) {
            CurrentIndex--;
        }
        return students.get(CurrentIndex);
    }

    // Get the current student
    public Student getCurrentStudent() {
        if (CurrentIndex >= 0 && CurrentIndex < students.size()) {
            return students.get(CurrentIndex);
        }
        return null;
    }

    // Print all students or the current student
    public void printStudents(boolean printAll) {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students to print.");
        } else {
            if (printAll) {
                students.forEach(System.out::println);
            } else if (CurrentIndex >= 0) {
                System.out.println(students.get(CurrentIndex));
            }
        }
    }

    // Get all students
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    // Get a student by registration number (or any unique detail)
    public Student getStudentByDetails(String regNumber) {
        for (Student student : students) {
            if (student.regNumber.equals(regNumber)) {
                return student;
            }
        }
        return null; // Return null if no student found
    }

    // Print a single student's details
    public void printSingleStudent(Student student) {
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("No student available to print.");
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getCurrentIndex() {
        return CurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        CurrentIndex = currentIndex;
    }
}
