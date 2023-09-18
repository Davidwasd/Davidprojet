/*
Author: Tneh Kai Qing
*/

package dao;

/**
 *
 * @author user
 */
import adt.ArrayList;
import entity.TutorialGroups;
import entity.students;
import adt.CustomHashMap;
import adt.CustomLinkedList;
import java.util.Scanner;
import utility.InputUtility;
import java.text.SimpleDateFormat;
import java.util.Date;
import adt.TutorialGroupManagementInterface;
import entity.students;
import entity.History;
import entity.Programme;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TutorialGroupDAO<T extends students, E extends TutorialGroups, F extends History, G extends Programme> implements TutorialGroupManagementInterface<T, E, F> {

    private CustomHashMap<String, T> students = new CustomHashMap<>();
    private CustomHashMap<String, E> tutorialGroups = new CustomHashMap<>();
    private CustomHashMap<String, F> historys = new CustomHashMap<>();
    private CustomHashMap<String, History> historyList = new CustomHashMap<>();
    private static ProgrammeManager<Programme, TutorialGroups> myList = new ProgrammeManager<>();
    private static ArrayList<Programme> programmeList = myList.getProgrammes();
    private F newHistory;
    private String timestamp;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);

    public TutorialGroupDAO(ProgrammeManager<Programme, TutorialGroups> myList) {
        this.myList = myList;
    }

    private static Scanner scanner = new Scanner(System.in);

    public String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    @Override
    public void addStudent(T student, F History) {
        // Add the student to the students HashMap
        students.put(student.getStudentID(), student);
        historys.put(History.getTimestamp(), History);
    }

    public void removeStudentFromTutorialGroup(Programme selectedProgramme, TutorialGroups selectedTutorialGroup, String studentID, students student) {
        if (selectedProgramme != null && selectedTutorialGroup != null) {
            CustomHashMap<String, students> studentsMap = selectedTutorialGroup.getStudents();
            String timestamp1 = generateTimestamp();
            if (studentsMap.containsKey(studentID)) {
                studentsMap.remove(studentID);
                int currentEnrolledStudents = selectedTutorialGroup.getEnrolledStudents();
                selectedTutorialGroup.setEnrolledStudents(currentEnrolledStudents - 1);
                System.out.println("");
                System.out.println("Student with ID " + studentID + " removed from Tutorial Group " + selectedTutorialGroup.getGroupID());

            } else {
                System.out.println("");
                System.out.println("Student with ID " + studentID + " not found in Tutorial Group " + selectedTutorialGroup.getGroupID());
            }
        } else {
            System.out.println("");
            System.out.println("Invalid selection. Please select a programme and a tutorial group first.");
        }
    }

    public void changeTutorialGroup(Programme selectedProgramme, int groupNumber, TutorialGroups selectedTutorialGroup, Programme selectedProgramme2, TutorialGroups newGroup, students student) {

//        displayTutorialGroups(selectedProgramme);
        if (student != null) {
            // Step 1: Remove Student from Current Group
            String idTD = student.getStudentID();

            newGroup.setEnrolledStudents(newGroup.getEnrolledStudents() + 1);

            // Step 2: Add Student to New Group
            removeStudentFromTutorialGroup(selectedProgramme, selectedTutorialGroup, idTD, student);         
            newGroup.addStudent(student);

            String newTutorialGroup = newGroup.getGroupID();

            if (newTutorialGroup != null) {
                selectedTutorialGroup.setEnrolledStudents(selectedTutorialGroup.getEnrolledStudents() + 1);
                student.setTutorialGroup(newTutorialGroup);
                selectedTutorialGroup.addStudent(student);

            }

            System.out.println("Student's tutorial group changed successfully.");
        } else {
            System.out.println("Student not found in the selected tutorial group.");
        }
    }

    @Override
    public CustomHashMap<String, students> getStudents() {
        CustomHashMap<String, students> allStudents = new CustomHashMap<>();

        for (TutorialGroups tutorialGroup : tutorialGroups.values()) {
            CustomHashMap<String, students> studentsInGroup = tutorialGroup.getStudents();

            for (students student : studentsInGroup.values()) {
                allStudents.put(student.getStudentID(), student);
            }
        }

        return allStudents;
    }

    @Override
    public CustomHashMap<String, F> getHistory() {
        return historys;
    }

    @Override
    public void editTutorialGroup(E updatedTutorialGroup, Programme programme) {
        String groupID = updatedTutorialGroup.getGroupID();
        Programme targetProgramme = null;
        TutorialGroups targetTutorialGroup = null;
        ArrayList<TutorialGroups> tutorialGroups = programme.getTutorialGroups();

        for (int i = 1; i <= tutorialGroups.size(); i++) {
            TutorialGroups group = tutorialGroups.getEntry(i);
            if (group.getGroupID().equals(groupID)) {
                targetProgramme = programme;
                targetTutorialGroup = group;
                if (targetTutorialGroup != null) {
                    break;
                }
            }
            if (targetProgramme != null && targetTutorialGroup != null) {
                // Update the TutorialGroups in the Programme
                String programID = targetProgramme.getProgrammeID();
                myList.deleteTutorialGroupFromProgramme(groupID, programID);
                myList.addTutorialGroupToProgramme(updatedTutorialGroup, programID);
                System.out.println("Tutorial group updated successfully.");
            } 
            break;
        }
    }

    @Override
    public TutorialGroups findTutorialGroup(String groupID) {
        return tutorialGroups.get(groupID);
    }

    public ArrayList<TutorialGroups> filterTutorialGroups(
            String groupIDFilter,
            Programme filterProgram,
            int minEnrolledStudentsFilter,
            int maxCapacityFilter
    ) {
        ArrayList<TutorialGroups> filteredGroups = new ArrayList<>();

        ArrayList<TutorialGroups> tutorialGroups = filterProgram.getTutorialGroups();
        int numberOfEntries = tutorialGroups.getNumberOfEntries();
        for (int i = 0; i <= numberOfEntries; i++) {
            TutorialGroups group = tutorialGroups.getEntry(i);
            boolean meetsCriteria = true;
            if (group != null) {
                if (!groupIDFilter.isEmpty() && !group.getGroupID().equalsIgnoreCase(groupIDFilter)) {
                    meetsCriteria = false;
                }

                if (minEnrolledStudentsFilter != -1 && group.getEnrolledStudents() < minEnrolledStudentsFilter) {
                    meetsCriteria = false;
                }

                if (maxCapacityFilter != -1 && group.getCapacity() > maxCapacityFilter) {
                    meetsCriteria = false;
                }

                if (meetsCriteria) {
                    filteredGroups.add(group);
                    return filteredGroups;
//                }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public CustomHashMap<String, CustomHashMap<String, History>> getHistoryByTutorialGroup() {
        CustomHashMap<String, CustomHashMap<String, History>> historyByGroup = new CustomHashMap<>();
        ArrayList<Programme> allProgrammes = myList.getProgrammes();
        int numberOfEntries = allProgrammes.getNumberOfEntries();

        // Iterate through each tutorial group
        for (int i = 1; i <= numberOfEntries; i++) {

            CustomHashMap<String, History> historyForGroup = new CustomHashMap<>();
            String id = "";
            // Iterate through all histories to find ones related to the tutorial group
            for (History history : historys.values()) {
                Programme program = allProgrammes.getEntry(i);
                int numberOfEntries1 = program.getTutorialGroups().getNumberOfEntries();

                for (int j = 1; j <= numberOfEntries1; j++) {
                    TutorialGroups tutorialGroup = program.getTutorialGroups().get(j);
                    if (history.getGroup().contains(tutorialGroup.getGroupID())) {
                        String timestamp1 = generateTimestamp();

                        historyForGroup.put(timestamp1, history);
                    }
                    id = tutorialGroup.getGroupID();

                }

            }

            // Add the histories for the tutorial group to the result map
            historyByGroup.put(id, historyForGroup);
        }

        return historyByGroup;
    }

    public void displayAllDataFromHistoryMap() {
        System.out.println("");
        System.out.println("** Displaying All Data from History Map **");

        for (History history : historys.values()) {
            System.out.println("Timestamp: " + history.getTimestamp());
            System.out.println("Action: " + history.getAction());
            System.out.println("Description: " + history.getDescription());
            System.out.println("Group: " + history.getGroup());
            System.out.println();
        }
    }

    public void printHistoryByTutorialGroup() {
        System.out.println("Log File of Changes:");
        ArrayList<Programme> allProgrammes = myList.getProgrammes();
        int numberOfEntries = allProgrammes.getNumberOfEntries();

        // Iterate through each tutorial group
        for (int i = 1; i <= numberOfEntries; i++) {
            String id = "";
            CustomHashMap<String, History> historyForGroup = new CustomHashMap<>();
            Programme program = allProgrammes.getEntry(i);
            int numberOfEntries1 = program.getTutorialGroups().getNumberOfEntries();

            // Iterate through all histories to find ones related to the tutorial group
            for (History history : historys.values()) {
                for (int j = 1; j <= numberOfEntries1; j++) {
                    TutorialGroups tutorialGroup = program.getTutorialGroups().get(j);
                    if (history.getGroup().contains(tutorialGroup.getGroupID())) {
                        String timestamp1 = generateTimestamp();
                        historyForGroup.put(history.getTimestamp(), history);
                    }
                    id = tutorialGroup.getGroupID();
                }
            }

            // Print information for the tutorial group
            System.out.println("Tutorial Group: " + id);
            System.out.println("Programme: " + program.getProgrammeID());

            int historyCounter = 1;

            // Iterate through history entries for the current group
            for (History history : historyForGroup.values()) {
                System.out.println(historyCounter++ + ". " + history.getAction());
                System.out.println(history.getTimestamp());
                System.out.print("Student Details:");
                System.out.println(history.getDescription());
            }

            System.out.println(); // Add a separator between groups
        }
    }

    public void generateReport() {
        System.out.println("");
        ArrayList<Programme> allProgrammes = myList.getProgrammes();
        int numberOfEntries = allProgrammes.getNumberOfEntries();

        if (!allProgrammes.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();

            System.out.println("** Tutorial Group Summary Report**");
            System.out.println("Report on date: " + dateFormat.format(currentDate));

            // Student Enrollment Statistics
            int totalStudents = 0;
            for (int i = 1; i <= numberOfEntries; i++) {
                Programme program = allProgrammes.getEntry(i);
                int numberOfEntries1 = program.getTutorialGroups().getNumberOfEntries();

                for (int j = 1; j <= numberOfEntries1; j++) {
                    TutorialGroups tutorialGroup = program.getTutorialGroups().get(j);

                    totalStudents += tutorialGroup.getStudents().size();
                }
            }

            System.out.println("\nStudent Enrollment Statistics Verification:");
            System.out.println("Total Students Enrolled: " + totalStudents);
            System.out.println();
            System.out.println("Enrollment Data In Database:");

            for (int i = 1; i <= numberOfEntries; i++) {
                Programme program = allProgrammes.get(i);
                int numberOfEntries2 = program.getTutorialGroups().getNumberOfEntries();

                for (int j = 1; j <= numberOfEntries2; j++) {
                    TutorialGroups tutorialGroup = program.getTutorialGroups().get(j);
                    String groupID = tutorialGroup.getGroupID();
                    int enrolledStudents = tutorialGroup.getEnrolledStudents();

                    System.out.println("Tutorial Group: " + groupID);
                    System.out.println("Enrolled Students: " + enrolledStudents);
                    System.out.println();
                }
            }
        } else {
            System.out.println("Error, please try again");
        }
    }

    private CustomHashMap<String, Integer> calculateProgramEnrollments() {
        ArrayList<Programme> allProgrammes = myList.getProgrammes();
        int numberOfEntries = allProgrammes.getNumberOfEntries();
        CustomHashMap<String, Integer> programEnrollments = new CustomHashMap<>();

        // Iterate through all programs
        for (int i = 1; i <= numberOfEntries; i++) {
            Programme program = allProgrammes.get(i);
            ArrayList<TutorialGroups> tutorialGroups = program.getTutorialGroups();

            for (int j = 1; j <= tutorialGroups.size(); j++) {
                TutorialGroups group = tutorialGroups.get(j);
                CustomHashMap<String, students> studentsMap = group.getStudents();

                for (students student : studentsMap.values()) {
                    String programName = student.getProgramme();

                    // Check if the program is already in the map
                    if (programEnrollments.containsKey(programName)) {
                        int currentEnrollment = programEnrollments.get(programName);
                        programEnrollments.put(programName, currentEnrollment + 1);
                    } else {
                        // If the program is not in the map, add it with an enrollment count of 1
                        programEnrollments.put(programName, 1);
                    }
                }
            }
        }

        return programEnrollments;
    }

    private CustomHashMap<String, Integer> calculateGroupEnrollments() {
        ArrayList<Programme> allProgrammes = myList.getProgrammes();
        int numberOfEntries = allProgrammes.getNumberOfEntries();
        CustomHashMap<String, Integer> groupEnrollments = new CustomHashMap<>();

        // Iterate through all programs
        for (int i = 1; i <= numberOfEntries; i++) {
            Programme program = allProgrammes.get(i);
            ArrayList<TutorialGroups> tutorialGroups = program.getTutorialGroups();

            for (int j = 1; j < tutorialGroups.size(); j++) {
                TutorialGroups group = tutorialGroups.get(j);
                CustomHashMap<String, students> studentsMap = group.getStudents();

                for (students student : studentsMap.values()) {
                    String tutorialGroup = student.getTutorialGroup();

                    // Check if the tutorial group is already in the map
                    if (groupEnrollments.containsKey(tutorialGroup)) {
                        int currentEnrollment = groupEnrollments.get(tutorialGroup);
                        groupEnrollments.put(tutorialGroup, currentEnrollment + 1);
                    } else {
                        // If the tutorial group is not in the map, add it with an enrollment count of 1
                        groupEnrollments.put(tutorialGroup, 1);
                    }
                }
            }
        }

        return groupEnrollments;
    }

    public void calculateGroupEnrollmentStatistics() {
        CustomHashMap<String, Integer> groupEnrollments = calculateGroupEnrollments();

        // Now, groupEnrollments contains the enrollment statistics for each tutorial group
        System.out.println("Tutorial Group Enrollment Statistics:");

        for (String group : groupEnrollments.keys()) {
            int enrollment = groupEnrollments.get(group);
            System.out.println("Tutorial Group: " + group + ", Enrollment: " + enrollment);
        }
    }

    public String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public void TutorialGroupEnrollmentStatusReport() {
        ArrayList<Programme> allProgrammes = myList.getProgrammes();      
        int numberOfEntries = allProgrammes.getNumberOfEntries();
        System.out.println("");
        System.out.println("Tutorial Group Enrollment Status Report:");
        for (int i = 1; i <= numberOfEntries; i++) {
            Programme program = allProgrammes.get(i);
            int numberOfEntries2 = program.getTutorialGroups().getNumberOfEntries();

            for (int j = 1; j <= numberOfEntries2; j++) {
                TutorialGroups tutorialGroup = program.getTutorialGroups().get(j);
                String groupID = tutorialGroup.getGroupID();
                int enrolledStudents = tutorialGroup.getEnrolledStudents();
                String program1 = tutorialGroup.getProgramme();
                int capacity = tutorialGroup.getCapacity();
                String status;
                int availableSlots = 0;

                // Determine the status based on enrollment
                if (enrolledStudents < 10) {
                    status = "Low";
                    availableSlots = capacity - enrolledStudents;
                } else if (enrolledStudents < capacity) {
                    status = "Open";
                    availableSlots = capacity - enrolledStudents;
                } else if (enrolledStudents == capacity) {
                    status = "Full";
                    availableSlots = capacity - enrolledStudents;
                } else {
                    status = "Over Capacity";
                    availableSlots = capacity - enrolledStudents;
                }

                // Print group details and status, including available slots for "Open" status
                System.out.println("Group ID: " + groupID);
                System.out.println("Program: " + program1);
                System.out.println("Enrolled Students: " + enrolledStudents);
                System.out.println("Capacity: " + capacity);
                System.out.println("Status: " + status);
                System.out.println("Available Slots: " + availableSlots);

                System.out.println(); // Add spacing between groups

            }
        }
        System.out.println("Student are encourage to join the tutorial group with 'Low' status!!!");

    }

    public void generateEnrollmentTrendsReport() {
        System.out.println("Enrollment Trends:");

        // Initialize variables to keep track of enrollment changes
        int totalEnrollments = 0;
        int enrollmentsAdded = 0;
        int enrollmentsRemoved = 0;

        CustomHashMap<String, CustomHashMap<String, History>> historyByGroup = getHistoryByTutorialGroup();

        // Iterate through history entries for each group
        for (String groupID : historyByGroup.keys()) {
            CustomHashMap<String, History> historyMap = historyByGroup.get(groupID);

            // Iterate through history entries for the current group
            for (History history : historyMap.values()) {
                String action = history.getAction();

                // Assuming action format: "Student: [name] ([studentID]) has been Added to [programme] [tutorialGroup]"
                if (action.startsWith("Student:") && action.contains("has been Added to")) {
                    enrollmentsAdded++;
                    totalEnrollments++;
                } else if (action.startsWith("Student:") && action.contains("has been Removed from")) {
                    enrollmentsRemoved++;
                    totalEnrollments--;
                }
                System.out.println("Last Changes:");
                System.out.println("Group ID: " + groupID);
                System.out.println("Timestamp: " + history.getTimestamp());
                System.out.println("Action: " + action);
                System.out.println("Description: " + history.getDescription());
                System.out.println();
            }
        }

        // Print summary statistics
        System.out.println("Total Enrollments: " + totalEnrollments);
        System.out.println("Enrollments Added: " + enrollmentsAdded);
        System.out.println("Enrollments Removed: " + enrollmentsRemoved);
        System.out.println("");
    }

    public void displayTutorialGroupsAndStudents() {
        System.out.println("Tutorial Groups and Enrolled Students:");

        for (TutorialGroups group : tutorialGroups.values()) {
            System.out.println("Group ID: " + group.getGroupID());
            System.out.println("Programme: " + group.getProgramme());
            System.out.println("Enrolled Students: " + group.getEnrolledStudents());
            System.out.println("Capacity: " + group.getCapacity());

            System.out.println("Students:");
            for (students student : group.getStudents().values()) {
                System.out.println("Student ID: " + student.getStudentID());
                System.out.println("Name: " + student.getName());
                System.out.println("Programme: " + student.getProgramme());
                System.out.println("Email: " + student.getEmail());
                System.out.println("Phone Number: " + student.getPhoneNumber());
                System.out.println("Address: " + student.getAddress());
                System.out.println();
            }

            System.out.println(); // Add spacing between groups
        }
    }

    public void addHistory(F history) {
        String timestamp1 = generateTimestamp();
        historys.put(timestamp1, history);
    }

    public void displayStudentsInTutorialGroup(Programme selectedProgramme, TutorialGroups selectedTutorialGroup) {
        if (selectedProgramme != null && selectedTutorialGroup != null) {
            System.out.println("Selected Tutorial Group: " + selectedTutorialGroup.getGroupID());
            System.out.println("Programme: " + selectedTutorialGroup.getProgramme());

            CustomHashMap<String, students> studentsMap = selectedTutorialGroup.getStudents();

            for (students student : studentsMap.values()) {
                System.out.println("Student ID: " + student.getStudentID());
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
                // Add more details as needed
            }
        } else {
            System.out.println("Invalid selection. Please select a programme and a tutorial group first.");
        }
    }

    public CustomHashMap<String, students> getStudentsInTutorialGroup(Programme selectedProgramme, TutorialGroups selectedTutorialGroup) {
        CustomHashMap<String, students> studentsMap = new CustomHashMap<>();

        if (selectedProgramme != null && selectedTutorialGroup != null) {
            System.out.println("Selected Tutorial Group: " + selectedTutorialGroup.getGroupID());
            System.out.println("Programme: " + selectedTutorialGroup.getProgramme());

            CustomHashMap<String, students> selectedMap = selectedTutorialGroup.getStudents();

            for (students student : selectedMap.values()) {
                selectedMap.put(student.getStudentID(), student);
            }
        } else {
            System.out.println("Invalid selection. Please select a programme and a tutorial group first.");
        }

        return studentsMap;
    }

    public void displayTutorialGroups(Programme programme) {
        ArrayList<TutorialGroups> tutorialGroups = programme.getTutorialGroups();
//        System.out.println( tutorialGroups );
        if (tutorialGroups.isEmpty()) {
            System.out.println("No tutorial groups available for this program.");
            return;
        }

        for (int i = 1; i <= tutorialGroups.size(); i++) {
            TutorialGroups group = tutorialGroups.get(i);
            System.out.println((i) + ". " + group.getGroupID() + " (" + group.getEnrolledStudents() + "/" + group.getCapacity() + ")");
        }
    }

    public TutorialGroups selectTutorialGroup(Programme programme, int groupNumber) {
        ArrayList<TutorialGroups> tutorialGroups = programme.getTutorialGroups();
        if (groupNumber >= 1 && groupNumber <= tutorialGroups.size()) {
            TutorialGroups group = tutorialGroups.getEntry(groupNumber);
            return group;
        } else {
            return null;
        }
    }

    public void viewStudentDetails(CustomHashMap<String, students> foundStudents) {
        System.out.println("");
        if (!foundStudents.isEmpty()) {
            System.out.println("Students Found:");
            int studentCounter = 1;
            for (students student : foundStudents.values()) {
                System.out.println(studentCounter + ". " + student.getName() + " (" + student.getStudentID() + ")");
                studentCounter++;
            }

            int selectedStudentNumber = InputUtility.promptIntInput("Enter the number of the student to view details: ");
            if (selectedStudentNumber >= 1 && selectedStudentNumber < studentCounter) {
                students selectedStudent = foundStudents.getValueAt(selectedStudentNumber - 1);
                System.out.println("");
                selectedStudent.displayStudentInfo(); // Display student info
            } else {
                System.out.println("Invalid student selection.");
            }
        } else {
            System.out.println("No students found in the specified tutorial group matching the search.");
        }
    }

    public CustomHashMap<String, students> getAllStudentsInGroup(TutorialGroups selectedGroup) {
        CustomHashMap<String, students> studentsInGroup = new CustomHashMap<>();

        if (selectedGroup != null) {
            CustomHashMap<String, students> studentsMap = selectedGroup.getStudents();

            for (students student : studentsMap.values()) {
                studentsInGroup.put(student.getStudentID(), student);
            }
        }

        return studentsInGroup;
    }

    public students selectStudentInGroup(TutorialGroups selectedGroup) {
        int counter = 1;
        for (students student : selectedGroup.getStudents().values()) {
            System.out.println(counter + ". " + student.getName() + student.getProgramme() + selectedGroup.getGroupID() + "(" + student.getStudentID() + ")");
            counter++;
        }
        if ((!selectedGroup.getStudents().isEmpty())) {
            System.out.println("");
            int selectedNumber = InputUtility.promptIntInput("Enter the number of the student: ");
            if (selectedNumber >= 1 && selectedNumber < counter) {
                int studentIndex = 1;
                for (students student : selectedGroup.getStudents().values()) {
                    if (studentIndex == selectedNumber) {
                        return student;
                    }
                    studentIndex++;
                }
            }

        } else {
            System.out.println("Error, No Student Available");
        }
        return null; // Invalid selection
    }

    public boolean verifyStudentRemoval(students selectedStudent) {
        System.out.println("ID: " + selectedStudent.getStudentID());
        System.out.println("Name: " + selectedStudent.getName());
        System.out.println("Programme: " + selectedStudent.getProgramme());
        System.out.println("Email: " + selectedStudent.getEmail());
        System.out.println("Phone Number: " + selectedStudent.getPhoneNumber());
        System.out.println("Address: " + selectedStudent.getAddress());
        System.out.println();
        String studentIdToVerify = InputUtility.promptInput("Enter student ID to continue: ");
        return studentIdToVerify.equalsIgnoreCase(selectedStudent.getStudentID());
    }

    public boolean studentContains(TutorialGroups tutorialGroup, String query) {
        for (students student : tutorialGroup.getStudents().values()) {
            if (student.getStudentID().contains(query)
                    || student.getName().toLowerCase().contains(query.toLowerCase())
                    || student.getProgramme().toLowerCase().contains(query.toLowerCase())
                    || student.getTutorialGroup().equalsIgnoreCase(query)
                    || student.getEmail().equalsIgnoreCase(query)
                    || student.getPhoneNumber().equalsIgnoreCase(query)
                    || student.getAddress().toLowerCase().contains(query.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void listAllStudentsInSelectedGroup(CustomHashMap<String, students> studentsInGroup) {
        if (!studentsInGroup.isEmpty()) {
            System.out.println("Students in selected tutorial group:");
            for (students student : studentsInGroup.values()) {
                System.out.println("ID: " + student.getStudentID());
                System.out.println("Name: " + student.getName());
                System.out.println("Programme: " + student.getProgramme());
                System.out.println("Email: " + student.getEmail());
                System.out.println("Phone Number: " + student.getPhoneNumber());
                System.out.println("Address: " + student.getAddress());
                System.out.println();
            }
        } else {
            System.out.println("No students in the selected tutorial group.");
        }
    }

    @Override
    public CustomHashMap<String, students> searchStudentsByGroup(Programme selectedProgramme, TutorialGroups selectedGroup) {
        System.out.print("Search (press enter to list all students): ");
        String search = scanner.nextLine();

        CustomHashMap<String, students> foundStudents = new CustomHashMap<>();
        CustomHashMap<String, students> studentsMap = selectedGroup.getStudents();

        for (students student : studentsMap.values()) {
            if (studentContains(selectedGroup, search)) {
                foundStudents.put(student.getStudentID(), student);
            }
        }

        return foundStudents;
    }
    private static int studentCount = 1;
    
    
    @Override
    public String generateStudentID(String programmeID) {
        
        // Get the current year
        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
        String year = yearFormat.format(new Date());

        // Create the student ID
        String studentID = year + programmeID + String.format("%05d", studentCount);
        
        // Increment the student count for the next ID
        studentCount++;

        return studentID;
    }
    
   

}
