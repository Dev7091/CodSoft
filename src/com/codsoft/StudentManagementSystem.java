package com.codsoft;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JTextArea;

public class StudentManagementSystem {
    private List<Student> students;
    private Set<Integer> rollNumbers; // Keep track of existing roll numbers
    private String dataFilePath = "students.csv"; // The file to store student data

    public StudentManagementSystem() {
        students = new ArrayList<>();
        rollNumbers = new HashSet<>();
        loadStudents(); // Load students from the file on startup
    }

    public boolean addStudent(Student student) {
        int rollNumber = student.getRollNumber();
        if (rollNumbers.contains(rollNumber)) {
            System.out.println("Roll number " + rollNumber + " already exists. Duplicate entry ignored.");
            return false;
        }

        students.add(student);
        rollNumbers.add(rollNumber);
        saveStudents(); // Save students after adding a new student
        return true;
    }
    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        rollNumbers.remove(rollNumber);
        saveStudents(); // Save students after removing a student
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null; // Student not found
    }

    public void displayAllStudents(JTextArea outputArea) {
        if (students.isEmpty()) {
            outputArea.setText("No students found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Student student : students) {
                sb.append(student.getName()).append(" | Roll Number: ")
                        .append(student.getRollNumber()).append(" | Grade: ").append(student.getGrade())
                        .append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFilePath))) {
            students = (List<Student>) ois.readObject();
            // Populate the rollNumbers set from loaded students
            for (Student student : students) {
                rollNumbers.add(student.getRollNumber());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No data file found. Starting with an empty student list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data.");
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

}
