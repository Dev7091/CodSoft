package com.codsoft;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;
    // Add any other relevant details here

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

    // Getters and Setters
    // Add other methods as needed
}
