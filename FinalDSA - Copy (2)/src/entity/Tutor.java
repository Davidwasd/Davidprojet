/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Lim Yek Sean
 */
public class Tutor {
    private String tutorID;
    private String name;
    private String phoneNo;
    private String email;
    private double salaryRate;

    public Tutor(String tutorID, String name, String phoneNo, String email, double salaryRate) {
        this.tutorID = tutorID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.salaryRate = salaryRate;
    }

    // Getters and setters for all attributes

    public String getTutorID() {
        return tutorID;
    }
    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(double salaryRate) {
        this.salaryRate = salaryRate;
    }

    @Override
    public String toString() {
        return "Tutor ID: " + tutorID + "\nName: " + name + "\nPhone: " + phoneNo + "\nEmail: " + email + "\nSalary Rate: RM" + salaryRate;
    }
}


