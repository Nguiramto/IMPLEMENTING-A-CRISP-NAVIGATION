package org.example;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel; // Import JTattoo LookAndFeel class for custom styling
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRegistration extends JFrame {
    private final JTextField nameField;
    private final JTextField regField;
    private final JTextField courseField;
    private final JTextField subjectField;
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;
    private final JDateChooser dateChooser;
    private StudentManager manager = new StudentManager();
    private final List<Student> studentList = new ArrayList<>();
    private final JComboBox<String> studentComboBox; // To list and select students

    public StudentRegistration() {
        setTitle("Student Registration");
        setSize(600, 600); // Increased size for additional buttons and functionalities
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Set JTattoo LookAndFeel for styling
        try {
            UIManager.setLookAndFeel(new AcrylLookAndFeel()); // Set a custom look and feel from JTattoo
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Registration No:"), gbc);
        gbc.gridx = 1;
        regField = new JTextField(20);
        add(regField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        courseField = new JTextField(20);
        add(courseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        add(dateChooser, gbc);

        // Submit button with MySQL connection and JTattoo integration
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the form data
                String name = nameField.getText();
                String regNo = regField.getText();
                String course = courseField.getText();
                String subject = subjectField.getText();
                String gender = maleButton.isSelected() ? "Male" : "Female";
                java.util.Date selectedDate = dateChooser.getDate();
                java.sql.Date sqlDate = selectedDate != null ? new java.sql.Date(selectedDate.getTime()) : null;

                // Create a new student object and add it to the list
                Student student = new Student(name, regNo, course, subject, gender, sqlDate);
                studentList.add(student);

                // Add to combo box for selecting students
                studentComboBox.addItem(student.getName());

                // Print student details to console
                System.out.println("Student added: " + student);

                // Reset the form for adding another student
                resetFormFields();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(submitButton, gbc);

        // ComboBox for selecting a student to view or delete
        studentComboBox = new JComboBox<>();
        studentComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudentName = (String) studentComboBox.getSelectedItem();
                Optional<Student> selectedStudent = studentList.stream()
                        .filter(student -> student.getName().equals(selectedStudentName))
                        .findFirst();
                if (selectedStudent.isPresent()) {
                    Student student = selectedStudent.get();
                    System.out.println("Selected Student: " + student);
                    nameField.setText(student.getName());
                    regField.setText(student.getRegNo());
                    courseField.setText(student.getCourse());
                    subjectField.setText(student.getSubject());
                    if ("Male".equals(student.getGender())) maleButton.setSelected(true);
                    else femaleButton.setSelected(true);
                    dateChooser.setDate(student.getDob());
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Select Student:"), gbc);
        gbc.gridx = 1;
        add(studentComboBox, gbc);

        // Delete student button
        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudentName = (String) studentComboBox.getSelectedItem();
                Optional<Student> selectedStudent = studentList.stream()
                        .filter(student -> student.getName().equals(selectedStudentName))
                        .findFirst();
                if (selectedStudent.isPresent()) {
                    studentList.remove(selectedStudent.get());
                    studentComboBox.removeItem(selectedStudent.get().getName());
                    System.out.println("Student deleted: " + selectedStudent.get());
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(deleteButton, gbc);

        // View profile button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudentName = (String) studentComboBox.getSelectedItem();
                Optional<Student> selectedStudent = studentList.stream()
                        .filter(student -> student.getName().equals(selectedStudentName))
                        .findFirst();
                if (selectedStudent.isPresent()) {
                    Student student = selectedStudent.get();
                    JOptionPane.showMessageDialog(null, "Name: " + student.getName() +
                            "\nRegistration No: " + student.getRegNo() +
                            "\nCourse: " + student.getCourse() +
                            "\nSubject: " + student.getSubject() +
                            "\nGender: " + student.getGender() +
                            "\nDate of Birth: " + student.getDob());
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(viewProfileButton, gbc);

        // List all students button
        JButton listButton = new JButton("List All Students");
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder studentListDetails = new StringBuilder();
                for (Student student : studentList) {
                    studentListDetails.append(student).append("\n");
                }
                JOptionPane.showMessageDialog(null, studentListDetails.toString(), "List of Students", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(listButton, gbc);
    }

    // Method to reset the form fields after adding a student
    private void resetFormFields() {
        nameField.setText("");
        regField.setText("");
        courseField.setText("");
        subjectField.setText("");
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        dateChooser.setDate(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentRegistration().setVisible(true);
            }
        });
    }

    public StudentManager getManager() {
        return manager;
    }

    public void setManager(StudentManager manager) {
        this.manager = manager;
    }
}
