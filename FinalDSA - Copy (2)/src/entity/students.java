/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author user
 */
public class students {
    private String studentID;
    private String name;
    private String programme;
    private String tutorialGroup;
    private String email;
    private String phoneNumber;
    private String address;

    public students(String studentID, String name, String programme, String tutorialGroup, String email, String phoneNumber, String address) {
        this.studentID = studentID;
        this.name = name;
        this.programme = programme;
        this.tutorialGroup = tutorialGroup;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(String tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void displayStudentInfo() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Programme: " + programme);
        System.out.println("Tutorial Group: " + tutorialGroup);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
    }

    @Override
    public String toString() {
        return "students{" + "studentID=" + studentID + ", name=" + name + ", programme=" + programme + ", tutorialGroup=" + tutorialGroup + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + '}';
    }
      
}