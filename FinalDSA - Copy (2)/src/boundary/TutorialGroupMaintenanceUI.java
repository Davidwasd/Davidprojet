/*
Author: Tneh Kai Qing
*/

package boundary;

import dao.TutorialGroupDAO;
import entity.TutorialGroups;
import entity.students;
import adt.CustomHashMap;
import adt.CustomLinkedList;
import utility.InputUtility;

import java.util.Scanner;
import adt.TutorialGroupManagementInterface;
import entity.History;
import java.text.SimpleDateFormat;
import java.util.Date;

import utility.InputValidator;
import java.util.Scanner;
import entity.*;
import adt.*;
import dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import utility.InputUtility;

/**
 *
 * @author user
 */
public class TutorialGroupMaintenanceUI {
    

    private static Scanner scanner = new Scanner(System.in);
    private static ProgrammeManager<Programme, TutorialGroups> myList = new ProgrammeManager<>();
    private static ArrayList<Programme> programmeList = myList.getProgrammes();
    private static TutorialGroupManagementInterface<students, TutorialGroups, History> tutorialGroupStud = new TutorialGroupDAO<>(myList);
    private String timestamp;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);

    public static void TutorialGroupMaintenanceUI() {

        students student1 = new students("23PMR01234", "John", "RSD", "G1", "john@gmail.com", "0123456789", "123456789");
//        students student2 = new students("23PMR01235", "Jane", "RSW", "G2", "jane@gmail.com", "0123459876", "987654321");

//        History history1 = new History(timestamp, "Student Added", student1.getName() + "" + "(" + student1.getStudentID() + ")", student1.getTutorialGroup());
//        History history2 = new History(timestamp, "Add Student", student2.getName() + student2.getStudentID() + student2.getTutorialGroup());


//        tutorialGroupStud.addStudentToGroup(group1.getGroupID(), student1, history1);
//        tutorialGroupStud.addStudent(student1, history1);
//        tutorialGroupStud.addStudent(student2, history2);
//        tutorialGroupStud.addTutorialGroup(group2);
        boolean isStudentMenuRunning = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String timestamp = dateFormat.format(currentDate);

        while (isStudentMenuRunning) {
            System.out.println("\nStudent Management Menu: ");
            System.out.println("__________________________________________________");
            System.out.println("|1. Add Student to Tutorial Group                |");
            System.out.println("|2. Remove Student from Tutorial Group           |");
            System.out.println("|3. Change Tutorial Group for Student            |");
            System.out.println("|4. Find Student in a Tutorial Group             |");
            System.out.println("|5. List all Student                             |");
            System.out.println("|6. Filter all student in a tutorial group       |");
            System.out.println("|7. Report                                       |");
            System.out.println("|8. Back to Main Menu                            |");
            System.out.println("--------------------------------------------------");
            System.out.println("");
            int studentChoice = InputUtility.promptIntInput("Enter your choice: ");
            System.out.println("");

            switch (studentChoice) {
                case 1:
                    //add student to a tutorial group
                    System.out.println("----------------Add Student----------------");
                    System.out.println("");

                    // Get the student details from the user
                    String name = InputUtility.promptInput("Enter student name: ");
                    System.out.println("");
                    String email = InputUtility.promptInput("Enter email: ");
                    System.out.println("");
                    String phoneNumber = InputUtility.promptInput("Enter phone number: ");
                    System.out.println("");
                    String address = InputUtility.promptInput("Enter address: ");
                    System.out.println("");
                    System.out.println("Choose a programme to join:");
                    Programme program = myList.selectProgramme();
                    String studentID = tutorialGroupStud.generateStudentID(program.getProgrammeID());
                    if (program != null) {
                        System.out.println("Selected Programme: " + program.getProgrammeName());
                        String programme = program.getProgrammeID();
                        tutorialGroupStud.displayTutorialGroups(program);
                        int groupNumber = InputUtility.promptIntInput("Choose a Tutorial Group to join:");
                        TutorialGroups selectedGroup = tutorialGroupStud.selectTutorialGroup(program, groupNumber);

                        if (selectedGroup != null) {
                            String tutorialGroups = selectedGroup.getGroupID();
                            System.out.println("Tutorial group " + tutorialGroups + "");
                            // Check if the tutorial group is at full capacity
                            if (selectedGroup.getEnrolledStudents() >= selectedGroup.getCapacity()) {
                                System.out.println("Tutorial group " + tutorialGroups + " is already at full capacity. Student cannot be added.");
                            } else {
                                students newStudent = new students(studentID, name, programme, tutorialGroups, email, phoneNumber, address);
                                History newhistory = new History(timestamp, "Student:" + newStudent.getName() + "(" + newStudent.getStudentID() + ")" + "has been Added to " + programme + newStudent.getTutorialGroup(), newStudent.getName() + newStudent.getStudentID() + newStudent.getTutorialGroup(), newStudent.getTutorialGroup());
//                                tutorialGroupStud.addStudentToGroup(tutorialGroups, newStudent, newhistory);
                                selectedGroup.setEnrolledStudents(selectedGroup.getEnrolledStudents() + 1);
                                selectedGroup.addStudent(newStudent);
                                tutorialGroupStud.addHistory(newhistory);
//                                            targetTutorialGroup.setEnrolledStudents(targetTutorialGroup.getEnrolledStudents() + 1);
                                tutorialGroupStud.editTutorialGroup(selectedGroup, program);
                                System.out.println("Student Added Successfully");
                            }

                        } else {
                            System.out.println("No tutorial group found for the selected programme.");
                        }
                    } else {
                        System.out.println("No programme selected.");
                    }

                    break;

                case 2:
                    //remove a student from tutorial groups
                    System.out.println("----------------Remove Student----------------");
                    System.out.println("");

                    Programme selectedProgramme = myList.selectProgramme();
                    if (selectedProgramme != null) {
                        System.out.println("Selected Programme: " + selectedProgramme.getProgrammeID());
                        String programme = selectedProgramme.getProgrammeID();
                        tutorialGroupStud.displayTutorialGroups(selectedProgramme);
                        int groupNumber = InputUtility.promptIntInput("Choose a Tutorial Group to join:");
                        // Select a tutorial group
                        TutorialGroups selectedTutorialGroup = tutorialGroupStud.selectTutorialGroup(selectedProgramme, groupNumber);
                        String gID = selectedTutorialGroup.getGroupID();
                        if (selectedTutorialGroup != null) {
                            // Remove a student from the selected tutorial group
                            System.out.println("Student Inside:" + gID);
                            students delStudent = tutorialGroupStud.selectStudentInGroup(selectedTutorialGroup);
                            if (delStudent != null) {
                                String time = tutorialGroupStud.generateTimestamp();
                                History dhistory = new History(time, "Student:" + delStudent.getName() + "(" + delStudent.getStudentID() + ")" + " has been REMOVE from " + delStudent.getTutorialGroup(), delStudent.getName() + delStudent.getStudentID() + delStudent.getTutorialGroup(), delStudent.getTutorialGroup());
                                tutorialGroupStud.addHistory(dhistory);
                                boolean isVerified = tutorialGroupStud.verifyStudentRemoval(delStudent);
                                if (isVerified) {
                                    String studentIDToRemove = delStudent.getStudentID();
                                    tutorialGroupStud.removeStudentFromTutorialGroup(selectedProgramme, selectedTutorialGroup, studentIDToRemove, delStudent);
                                }
                            } else {
                                System.out.println("Error, No Student Inside Selected Tutorial Group");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("----------------Change Tutorial Groups----------------");
                    System.out.println("");
                    System.out.println("Select Current Programme:");
                    Programme selectedProgramme1 = myList.selectProgramme(); // Implement selectProgramme function
                    if (selectedProgramme1 == null) {
                        System.out.println("Programme not found.");
                        return;
                    }

                    System.out.println("Selected Programme:" + selectedProgramme1.getProgrammeID());

                    System.out.println("Tutorial Groups:");
                    tutorialGroupStud.displayTutorialGroups(selectedProgramme1);
                    int groupNumber2 = InputUtility.promptIntInput("Choose Current Group: ");
                    TutorialGroups selectedTutorialGroup = tutorialGroupStud.selectTutorialGroup(selectedProgramme1, groupNumber2); // Implement selectTutorialGroup function
                    if (selectedTutorialGroup == null) {
                        System.out.println("Tutorial group not found.");
                        return;
                    }

                    System.out.println("Select New Programme or remain the same:");
                    Programme selectedProgramme2 = myList.selectProgramme(); // Implement selectProgramme function
                    if (selectedProgramme2 == null) {
                        System.out.println("Programme not found.");
                        return;
                    }

                    System.out.println("Tutorial Groups:");
                    tutorialGroupStud.displayTutorialGroups(selectedProgramme2);
                    int groupNumber3 = InputUtility.promptIntInput("Choose New Group: ");

                    TutorialGroups newGroup1 = tutorialGroupStud.selectTutorialGroup(selectedProgramme2, groupNumber3); // Implement selectTutorialGroup function
                    if (newGroup1 == null) {
                        System.out.println("Tutorial group not found.");
                        return;
                    }

                    System.out.println("");
                    System.out.println("Select Students:");
                    System.out.println("");

                    students student = tutorialGroupStud.selectStudentInGroup(selectedTutorialGroup);
                    if (student != null) {
                        System.out.println("ID: " + student.getStudentID());
                        System.out.println("Name: " + student.getName());
                        System.out.println("Programme: " + student.getProgramme());
                        System.out.println("Email: " + student.getEmail());
                        System.out.println("Phone Number: " + student.getPhoneNumber());
                        System.out.println("Address: " + student.getAddress());
                        System.out.println();
                        String time = tutorialGroupStud.generateTimestamp();
                        History newhistory = new History(time, "Student:" + student.getName() + "(" + student.getStudentID() + ")" + "has been CHANGED from" + selectedProgramme1.getProgrammeID() + student.getTutorialGroup() + "to " + selectedProgramme2.getProgrammeID() + selectedTutorialGroup.getGroupID() + student.getTutorialGroup(), student.getName() + student.getStudentID() + student.getTutorialGroup(), student.getTutorialGroup());
                        tutorialGroupStud.addHistory(newhistory);
                        tutorialGroupStud.changeTutorialGroup(selectedProgramme1, groupNumber2, selectedTutorialGroup, selectedProgramme2, newGroup1, student);
                        System.out.println("");

                        String studentID2 = student.getStudentID();
                    tutorialGroupStud.removeStudentFromTutorialGroup(selectedProgramme1, selectedTutorialGroup, studentID2, student);
                        System.out.println("");
                    } else {
                        System.out.println("Error, No Student Selected or Selected Student Not Available");
                    }
//
//                            // Display success message
//                            System.out.println("Tutorial group updated successfully.");
//                        } else {
//                            System.out.println("Invalid selection for new tutorial group.");
//                        }
//                    } else {
//                        System.out.println("Invalid selection for current tutorial group.");
//                    }
                    break;
                case 4:
                    System.out.println("----------------Search & View Student Details----------------");
                    System.out.println("");
                    System.out.println("Choose Current Programme:");
                    Programme selectedProgramme3 = myList.selectProgramme(); // Implement selectProgramme function
                    if (selectedProgramme3 == null) {
                        System.out.println("Programme not found.");
                        return;
                    }

                    System.out.println("Choose Tutorial Groups:");
                    tutorialGroupStud.displayTutorialGroups(selectedProgramme3);
                    int groupNumber4 = InputUtility.promptIntInput("Select Tutorial Group: ");
                    TutorialGroups selectedTutorialGroup5 = tutorialGroupStud.selectTutorialGroup(selectedProgramme3, groupNumber4); // Implement selectTutorialGroup function
                    if (selectedTutorialGroup5 == null) {
                        System.out.println("Tutorial group not found.");
                        return;
                    }
//
//                    TutorialGroups selectedGroup = tutorialGroupStud.selectTutorialGroup();
                    System.out.println("----------------Search & View Student Details----------------");
                    System.out.println("");
                    CustomHashMap<String, students> foundStudents = tutorialGroupStud.searchStudentsByGroup(selectedProgramme3, selectedTutorialGroup5);
                    System.out.println("");
                    tutorialGroupStud.viewStudentDetails(foundStudents);

                    break;
                case 5:
//                    TutorialGroups selectedGroup1 = tutorialGroupStud.selectTutorialGroup();
//                    CustomHashMap<String, students> studentttt = tutorialGroupStud.getAllStudentsInGroup(selectedGroup1);
//                    tutorialGroupStud.listAllStudentsInSelectedGroup(studentttt);
                    System.out.println("----------------List all student in a tutorial group----------------");
                    System.out.println("");
                    Programme selectedProgramme4 = myList.selectProgramme(); // Implement selectProgramme function
                    if (selectedProgramme4 == null) {
                        System.out.println("Programme not found.");
                        return;
                    }

                    System.out.println("Tutorial Groups:");
                    tutorialGroupStud.displayTutorialGroups(selectedProgramme4);
                    int groupNumber5 = InputUtility.promptIntInput("Select Tutorial Group: ");
                    System.out.println("");
                    TutorialGroups selectedTutorialGroup4 = tutorialGroupStud.selectTutorialGroup(selectedProgramme4, groupNumber5); // Implement selectTutorialGroup function
                    if (selectedTutorialGroup4 == null) {
                        System.out.println("Tutorial group not found.");
                        return;
                    }
                    CustomHashMap<String, students> studentttt = tutorialGroupStud.getAllStudentsInGroup(selectedTutorialGroup4);
                    tutorialGroupStud.listAllStudentsInSelectedGroup(studentttt);

//
                    break;
                case 6:
                    System.out.println("----------------Filter Tutorial Groups----------------");
                    System.out.println("");

                    // Get user input for filters
                    Programme filterProgram = myList.selectProgramme();
                    String groupIDFilter = InputUtility.promptInput("Enter Group ID to filter (leave empty to ignore): ");
                    System.out.println("");
                    int minEnrolledStudentsFilter = InputUtility.promptIntInput("Enter Min Enrolled Students (-1 to ignore): ");
                    System.out.println("");
                    int maxCapacityFilter = InputUtility.promptIntInput("Enter Max Capacity (-1 to ignore): ");

                    // Use the filterTutorialGroups method
// Call the filter function
                    ArrayList<TutorialGroups> filteredGroups = tutorialGroupStud.filterTutorialGroups(
                            groupIDFilter,
                            filterProgram,
                            minEnrolledStudentsFilter,
                            maxCapacityFilter
                    );

                    // Process the filtered groups
                    if (filteredGroups == null) {
                        System.out.println("");
                        System.out.println("No tutorial groups match the specified criteria.");
                    } else {
                        System.out.println("Filtered Tutorial Groups:");
                        int numberOfEntries = filteredGroups.getNumberOfEntries();

                        for (int i = 1; i <= numberOfEntries; i++) {
                            TutorialGroups group = filteredGroups.getEntry(i);
                            System.out.println("Group ID: " + group.getGroupID());
                            System.out.println("Programme: " + group.getProgramme());
                            System.out.println("Enrolled Students: " + group.getEnrolledStudents());
                            System.out.println("Capacity: " + group.getCapacity());
                            System.out.println("-----------------------");
                        }
                    }

                    break;
                case 7:
                    //report
                    Scanner scanner = new Scanner(System.in);
                    boolean exit = false;

                    while (!exit) {
                        System.out.println("Report Menu:");
                        System.out.println("1. Tutorial Group Summary Report");
                        System.out.println("2. Student Enrollment Report");
                        System.out.println("3. History");
                        System.out.println("0. Exit");
                        System.out.print("Enter your choice: ");

                        while (!scanner.hasNextInt()) {
                            System.out.print("Invalid input. Please enter a number:");
                            scanner.next(); // Clear the invalid input
                        }

                        int choice = scanner.nextInt();

                        switch (choice) {
                            case 1:
                                // Generate and display the Tutorial Group Enrollment Status Report
                                tutorialGroupStud.generateReport();
                                tutorialGroupStud.printHistoryByTutorialGroup();
                                break;
                            case 2:
                                // Generate and display the Student Enrollment History Report
                                tutorialGroupStud.TutorialGroupEnrollmentStatusReport();
                                tutorialGroupStud.generateEnrollmentTrendsReport();
                                break;
                            case 3:
                                System.out.println("History");
                                tutorialGroupStud.displayAllDataFromHistoryMap();
                            case 0:
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                break;
                        }
                    }

                    break;
                case 8:
                    isStudentMenuRunning = false;
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }

        System.out.println("Student Management Menu has been exited.");

    }


public TutorialGroupMaintenanceUI(ProgrammeManager<Programme, TutorialGroups> programmeList) {
    this.myList = programmeList;
}

}
