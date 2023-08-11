package com.codsoft;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SuppressWarnings("serial")
public class StudentManagementSystemUI extends JFrame {
    private final StudentManagementSystem sms = new StudentManagementSystem();

    private final JTextField nameField;
    private final JTextField rollNumberField;
    private final JTextField gradeField;
    private final JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementSystemUI() {
        setTitle("Student Management System");
        setSize(800, 600); // Increased size for a more spacious layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Student Management System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Added spacing between components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField();
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton searchButton = new JButton("Search Student");
        JButton displayButton = new JButton("Display All Students");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(rollNumberLabel);
        inputPanel.add(rollNumberField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(searchButton);
        inputPanel.add(displayButton);

        // Create table headers
        String[] tableHeaders = {"Name", "Roll Number", "Grade"};
        tableModel = new DefaultTableModel(tableHeaders, 0);
        studentTable = new JTable(tableModel);

        // Styling for the table
        studentTable.setShowGrid(true);
        studentTable.setGridColor(Color.LIGHT_GRAY);
        studentTable.setSelectionBackground(new Color(173, 216, 230));

        // Adding the table to a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        searchButton.addActionListener(e -> searchStudent());
        displayButton.addActionListener(e -> displayAllStudents());
        // Styling
        addButton.setBackground(new Color(0, 123, 255)); // Modern button color
        addButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(220, 53, 69));
        removeButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(40, 167, 69));
        searchButton.setForeground(Color.WHITE);
        displayButton.setBackground(new Color(255, 193, 7));
        displayButton.setForeground(Color.WHITE);

        // Adding Components to Frame
        add(headingPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);
    }


    private void addStudent() {
        String name = nameField.getText();
        String rollNumberText = rollNumberField.getText();
        String grade = gradeField.getText();

        if (name.isEmpty() || rollNumberText.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        boolean added = sms.addStudent(student);

        if (added) {
            tableModel.addRow(new Object[]{name, rollNumber, grade});
        } else {
            JOptionPane.showMessageDialog(this, "Roll number " + rollNumber + " already exists. Duplicate entry ignored.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
        }

        clearInputFields();
    }


    private void removeStudent() {
        String rollNumberText = rollNumberField.getText();

        if (rollNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Roll Number to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the student with the given roll number exists in the system
        Student student = sms.searchStudent(rollNumber);
        if (student == null) {
            // Student not found, display appropriate message
            JOptionPane.showMessageDialog(this, "Student with Roll Number " + rollNumber + " not found!", "Student Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sms.removeStudent(rollNumber);

        // Remove student row from the table
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if ((int) tableModel.getValueAt(i, 1) == rollNumber) {
                tableModel.removeRow(i);
                break;
            }
        }

        clearInputFields();
    }

    private void searchStudent() {
        String rollNumberText = rollNumberField.getText();

        if (rollNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Roll Number to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            // Clear previous search results from the table
            tableModel.setRowCount(0);

            // Add search results to the table
            tableModel.addRow(new Object[]{student.getName(), student.getRollNumber(), student.getGrade()});
        } else {
            JOptionPane.showMessageDialog(this, "Student not found!", "Student Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void displayAllStudents() {
        // Clear previous table data
        tableModel.setRowCount(0);

        // Add all students to the table
        for (Student student : sms.getAllStudents()) {
            tableModel.addRow(new Object[]{student.getName(), student.getRollNumber(), student.getGrade()});
        }
    }


    private void clearInputFields() {
        nameField.setText("");
        rollNumberField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystemUI().setVisible(true));
    }
}
