package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentRegistration extends JFrame {
    private final JTextField nameField;
    private final JTextField regField;
    private final JTextField courseField;
    private final JTextField subjectField;
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;
    private final StudentManager manager = new StudentManager();

    public StudentRegistration() {
        setTitle("Student Registration");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Registration Number
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Registration Number:"), gbc);
        gbc.gridx = 1;
        regField = new JTextField(20);
        add(regField, gbc);

        // Course
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        courseField = new JTextField(20);
        add(courseField, gbc);

        // Subject
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        add(subjectField, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 4;
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

        // Buttons Panel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));

        JButton saveBtn = new JButton("Save");
        JButton addBtn = new JButton("Add");
        JButton nextBtn = new JButton("Next");
        JButton removeBtn = new JButton("Remove");
        JButton printBtn = new JButton("Print");
        JButton prevBtn = new JButton("Previous");
        JButton updateBtn = new JButton("Update");
        JButton exitBtn = new JButton("Exit");
        JButton profileBtn = new JButton("Profile");

        buttonPanel.add(saveBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(nextBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(printBtn);
        buttonPanel.add(prevBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(exitBtn);
        buttonPanel.add(profileBtn);

        add(buttonPanel, gbc);

        // Action Listeners
        saveBtn.addActionListener(e -> saveStudent());
        addBtn.addActionListener(e -> clearFields());
        nextBtn.addActionListener(e -> showNext());
        prevBtn.addActionListener(e -> showPrevious());
        removeBtn.addActionListener(e -> removeStudent());
        updateBtn.addActionListener(e -> showUpdateDialog());
        printBtn.addActionListener(e -> showPrintOptions());
        profileBtn.addActionListener(e -> showProfile());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void saveStudent() {
        // Get the selected gender value
        String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");

        // Get the subject value from the subjectField
        String subject = subjectField.getText();

        // Add the student
        manager.addStudent(nameField.getText(), regField.getText(), gender, courseField.getText(), subject);

        // Clear fields after saving
        clearFields();

        JOptionPane.showMessageDialog(this, "Student saved successfully!");
    }


    private void clearFields() {
        nameField.setText("");
        regField.setText("");
        courseField.setText("");
        subjectField.setText("");

        maleButton.setSelected(false);  // Ensure gender is cleared
        femaleButton.setSelected(false);
    }


    private void showNext() {
        if (manager.getAllStudents().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available.");
            return;
        }

        if (manager.getCurrentIndex() < manager.getAllStudents().size() - 1) {
            manager.setCurrentIndex(manager.getCurrentIndex() + 1);
        } else {
            JOptionPane.showMessageDialog(this, "You are at the last student.");
        }

        Student student = manager.getCurrentStudent();
        if (student != null) displayStudent(student);
    }

    private void showPrevious() {
        if (manager.getAllStudents().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available.");
            return;
        }

        if (manager.getCurrentIndex() > 0) {
            manager.setCurrentIndex(manager.getCurrentIndex() - 1);
        } else {
            JOptionPane.showMessageDialog(this, "You are at the first student.");
        }

        Student student = manager.getCurrentStudent();
        if (student != null) displayStudent(student);
    }

    private void showProfile() {
        List<Student> students = manager.getAllStudents();

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available.");
            return;
        }

        // Get list of student names and registration numbers
        String[] studentNames = students.stream().map(s -> s.name + " - " + s.regNumber).toArray(String[]::new);

        // Let the user select a student from the list
        String selectedStudent = (String) JOptionPane.showInputDialog(
                this,
                "Select a student to view their profile:",
                "Select Student",
                JOptionPane.QUESTION_MESSAGE,
                null,
                studentNames,
                studentNames[0]
        );

        if (selectedStudent != null) {
            // Extract the registration number from the selected string
            String regNumber = selectedStudent.split(" - ")[1];
            Student student = manager.getStudentByDetails(regNumber);

            if (student != null) {
                // Display the profile with the gender-based avatar
                ImageIcon avatar;
                if ("Male".equals(student.gender)) {
                    avatar = new ImageIcon("2b5406b6-65e7-4cef-84bd-a62c34dc0049.jpeg"); // Ensure these image files exist
                } else {
                    avatar = new ImageIcon("female-avatar-icon-young-attractive-woman-vector-14807848.jpeg"); // Ensure these image files exist
                }

                JOptionPane.showMessageDialog(this, student.toString(), "Student Profile", JOptionPane.INFORMATION_MESSAGE, avatar);
            }
        }
    }

    private void removeStudent() {
        manager.removeStudent();
        JOptionPane.showMessageDialog(this, "Student removed successfully!");
        clearFields();
    }

    private void showUpdateDialog() {
        List<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available to update.");
            return;
        }

        String[] studentNames = students.stream().map(s -> s.name + " - " + s.regNumber).toArray(String[]::new);
        String selectedStudent = (String) JOptionPane.showInputDialog(
                this,
                "Select a student to update:",
                "Update Student",
                JOptionPane.QUESTION_MESSAGE,
                null,
                studentNames,
                studentNames[0]
        );

        if (selectedStudent != null) {
            Student student = manager.getStudentByDetails(selectedStudent.split(" - ")[1]); // Extract regNumber
            if (student != null) {
                displayStudent(student);
            }
        }
    }

    private void showPrintOptions() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to print all students?",
                "Print Options",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            manager.printStudents(true);
        } else if (option == JOptionPane.NO_OPTION) {
            List<Student> students = manager.getAllStudents();
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students available to print.");
                return;
            }

            String[] studentNames = students.stream().map(s -> s.name + " - " + s.regNumber).toArray(String[]::new);
            String selectedStudent = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a student to print:",
                    "Print Student",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    studentNames,
                    studentNames[0]
            );

            if (selectedStudent != null) {
                Student student = manager.getStudentByDetails(selectedStudent.split(" - ")[1]);
                if (student != null) {
                    manager.printSingleStudent(student);
                }
            }
        }
    }

    private void displayStudent(Student student) {
        nameField.setText(student.name);
        regField.setText(student.regNumber);
        courseField.setText(student.course);
        subjectField.setText(student.subject);
        if ("Male".equals(student.gender)) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }
    }

    public static void main(String[] args) {
        new StudentRegistration();
    }
}
