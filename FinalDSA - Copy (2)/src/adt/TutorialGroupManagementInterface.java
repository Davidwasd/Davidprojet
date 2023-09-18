/*
Author: Tneh Kai Qing
*/


package adt;

import entity.History;
import entity.Programme;
import entity.TutorialGroups;
import entity.students;

/**
 *
 * @author user
 */
public interface TutorialGroupManagementInterface<T extends students, E extends TutorialGroups, F extends History> {

    void addStudent(T student, F historys);

//    void FillInStudent();
   

    void removeStudentFromTutorialGroup(Programme selectedProgramme, TutorialGroups selectedTutorialGroup, String studentID, students student);

   void changeTutorialGroup(Programme selectedProgramme, int groupNumber, TutorialGroups selectedTutorialGroup, Programme selectedProgramme2, TutorialGroups newGroup, students student);

    CustomHashMap<String, students> getStudents();

    void editTutorialGroup(E updatedTutorialGroup, Programme programme);

    void displayStudentsInTutorialGroup(Programme selectedProgramme, TutorialGroups selectedTutorialGroup);

    TutorialGroups findTutorialGroup(String groupID);

    ArrayList<TutorialGroups> filterTutorialGroups(
            String groupIDFilter,
            Programme filterProgram,
            int minEnrolledStudentsFilter,
            int maxCapacityFilter
    );

    void generateReport();

    void TutorialGroupEnrollmentStatusReport();

    CustomHashMap<String, F> getHistory();

    void displayTutorialGroupsAndStudents();

    void addHistory(F history);

    CustomHashMap<String, CustomHashMap<String, History>> getHistoryByTutorialGroup();

//    Programme selectProgramme();
    void displayTutorialGroups(Programme programme);

    TutorialGroups selectTutorialGroup(Programme programme, int groupNumber);

    void viewStudentDetails(CustomHashMap<String, students> foundStudents);

    CustomHashMap<String, students> getAllStudentsInGroup(TutorialGroups selectedGroup);

    students selectStudentInGroup(TutorialGroups selectedGroup);

    boolean verifyStudentRemoval(students selectedStudent);
    
    void printHistoryByTutorialGroup();
    void generateEnrollmentTrendsReport();
    
    void displayAllDataFromHistoryMap();
    
    String generateTimestamp();
    
    boolean studentContains(TutorialGroups tutorialGroup, String query);
    
    CustomHashMap<String, students> searchStudentsByGroup(Programme selectedProgramme, TutorialGroups selectedGroup);
    
    void listAllStudentsInSelectedGroup(CustomHashMap<String, students> studentsInGroup);
    
    String generateStudentID(String programmeID);
}
