/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Lim Yek Sean
 */
import entity.Tutor;
import adt.TutorInterface;

public class tutorController {
    private final TutorInterface<Tutor> tutorList;

    public tutorController(TutorInterface<Tutor> tutorList) {
        this.tutorList = tutorList;
    }

    public void addTutor(String tutorID, String name, String phoneNo, String email, double salaryRate) {
        tutorList.addTutor(new Tutor(tutorID, name, phoneNo,email,salaryRate));
    }

    public void editTutor(String tutorID, String newName, String newPhoneNo, String newEmail, double newSalaryRate) {
        tutorList.editTutor(tutorID, newName, newPhoneNo,newEmail,newSalaryRate);
    }

    public void removeTutor(String tutorID) {
        tutorList.removeTutor(tutorID);
    }
    
}
