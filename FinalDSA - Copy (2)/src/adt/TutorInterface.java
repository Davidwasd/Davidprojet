/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Lim Yek Sean
 */
import entity.Tutor;
public interface TutorInterface<T> {
    
    public boolean checkExist(String test);//to find ID at editTutor
    
    public void addTutor(Tutor tutor);//add Tutor
    
    public void editTutor(String tutorID, String newName, String newPhoneNo, String newEmail,double  newSalaryRate); 
    
    public void removeTutor(String tutorID);

    public void view();

    public void editName(String tutorID, String newName);
    
    public void generateSalaryReport();
}
