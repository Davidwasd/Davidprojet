
/*
Author: Oo Kai Jie
*/

package dao;

import adt.ArrayList;
import java.util.Scanner;
import adt.AssignmentTeamInterface;
import adt.CircularArrayQueue;
import adt.CustomHashMap;
import adt.TutorialGroupManagementInterface;
import adt.TutorialGroupManagementInterface;
import entity.History;
import entity.Programme;
import entity.TeamInfo;
import entity.TutorialGroups;
import entity.students;

public class AssignmentTeam<T extends TeamInfo> implements AssignmentTeamInterface<T> {
    private CircularArrayQueue<TeamInfo> Teams = new CircularArrayQueue<>();
    private ProgrammeManager<Programme, TutorialGroups> myList = new ProgrammeManager<>();
    private TutorialGroupManagementInterface<students, TutorialGroups, History> tutorialGroupStud = new TutorialGroupDAO<>(myList);
    Scanner scanner = new Scanner(System.in);
    
//    public AssignmentTeam(ProgrammeManager<Programme, TutorialGroups> myList){
//        this.myList = myList;
//    }
    
    public AssignmentTeam(TutorialGroups group){
        this.Teams = group.getTeam();
    }
    
    @Override
    public void createAssignmentTeam(T newTeam) {        
        
        if (!containTeam(newTeam.getGroup(), newTeam.getType(), newTeam.getTeamName())) {
            Teams.enqueue(newTeam);
            System.out.println();
            System.out.println(newTeam.getTeamName() + " has been added.");
            System.out.println("+----------------------------------------+");
            System.out.printf("| Programme: %-27s |\n", newTeam.getProgramme());
            System.out.printf("| Tutorial Group: %-22s |\n", newTeam.getGroup());
            System.out.printf("| Course: %-30s |\n", newTeam.getType());
            System.out.printf("| Team Name: %-27s |\n", newTeam.getTeamName());
            System.out.printf("| Description: %-25s |\n", newTeam.getDescription());
            System.out.printf("| Maximum Team Members: %-16d |\n", newTeam.getMaxNum());
            System.out.println("+----------------------------------------+");
            System.out.println();
        } else {
            System.out.println(newTeam.getTeamName() + " already existed.");
        }
    }

    @Override
    public void removeAssignmentTeam(T teams) {
        boolean removed = false;
        
        if(teams != null){
                    System.out.println("+----------------------------------------+");
                    System.out.printf("| Programme: %-27s |\n", teams.getProgramme());
                    System.out.printf("| Tutorial Group: %-22s |\n", teams.getGroup());
                    System.out.printf("| Course: %-30s |\n", teams.getType());
                    System.out.printf("| Team Name: %-27s |\n", teams.getTeamName());
                    System.out.printf("| Description: %-25s |\n", teams.getDescription());
                    System.out.printf("| Maximum Team Members: %-16d |\n", teams.getMaxNum());
                    System.out.println("+----------------------------------------+");
                    listStudentsByAssignmentTeam(teams);
                    System.out.println("Are you sure you want to delete this team ? (y/n)");
                    System.out.println("Enter your choice: ");
                    String opt = scanner.nextLine();
                    if(opt.equals("y")){
                        //Store the queue temporary
                        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>();
                        
                        //Compare the data
                        while (!Teams.isEmpty()) {
                            TeamInfo team = Teams.dequeue();
                            if (team.getType().equals(teams.getType())) {
                                if (team.getTeamName().equals(teams.getTeamName())) {
                                    System.out.println();
                                    System.out.println(teams.getTeamName() + " has been removed.");
                                    System.out.println();
                                    removed = true;
                                    break;
                                }
                            }
                            if (!removed) {
                                tempQueue.enqueue(team);
                            }
                        }
                        //Re-enter the data back to the ori-circular array queue
                        while (!tempQueue.isEmpty()) {
                            Teams.enqueue(tempQueue.dequeue());
                        }
                        
                        //Display error message
                        if (!removed) {
                            System.out.println();
                            System.out.println("Team " + teams.getTeamName() + " does not exist.\n");
                        }
                    }else{
                        System.out.println(teams.getTeamName() + " has not been removed.");
                    }
                    }else{
                        System.out.println(teams.getTeamName() + " not found.");
                    }
    }

    @Override
    public void amendAssignmentTeam(T teamToUpdate) {
        boolean found = false;

        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>(); // Use the same type T as the main queue
        while (!Teams.isEmpty()) {
            TeamInfo team = Teams.dequeue();
            if (team.getType().equals(teamToUpdate.getType()) && team.getTeamName().equals(teamToUpdate.getTeamName())) {
                team.setDescription(teamToUpdate.getDescription());
                team.setMaxNum(teamToUpdate.getMaxNum());
                team.setType(teamToUpdate.getType());
                tempQueue.enqueue(team);
                System.out.println();
                System.out.println(team.getTeamName() + "'s description has been updated.\n");
                found = true;
            } else {
                tempQueue.enqueue(team);
            }
        }

        while (!tempQueue.isEmpty()) {
            Teams.enqueue(tempQueue.dequeue());
        }

        if (!found) {
            System.out.println(teamToUpdate.getTeamName() + " does not exist.\n");
        }
    }

    @Override
    public void addStudentToTeam(students student, TeamInfo teamName) {
        
        if(!containStud(student, teamName)){
        TeamInfo team = teamName;
            if (team != null) {
                if (team.getStudentQueue().isFull()) {
                    System.out.println();
                    System.out.println("Team " + teamName.getTeamName() + " is full. Cannot add more students.\n");
                } else {
                    team.getStudentQueue().enqueue(student);
                    System.out.println();
                    System.out.println(student.getName() + " has been added to " + teamName.getType() + ": " + teamName.getTeamName() + "\n");
                }
            } else {
                System.out.println();
                System.out.println("Team " + teamName.getTeamName() + " not found.\n");
            }
        }
    }

    @Override
    public void removeStudentFromTeam(T removeTeam) {
        listStudentsByAssignmentTeam(removeTeam);
                    
        System.out.print("Enter student ID to remove: ");
        String studentID = scanner.nextLine();

        if (removeTeam != null) {
            CircularArrayQueue<students> studentQueue = removeTeam.getStudentQueue();
            CircularArrayQueue<students> tempQueue = new CircularArrayQueue<>();
            boolean studentRemoved = false;

            while (!studentQueue.isEmpty()) {
                students student = studentQueue.dequeue();
                if (student.getStudentID().equals(studentID)) {
                    System.out.println();
                    System.out.println("Removing student: " + student.getName() + " from team " + removeTeam.getTeamName() + "\n");
                    studentRemoved = true;
                } else {
                    tempQueue.enqueue(student);
                }
            }

            while (!tempQueue.isEmpty()) {
                students student = tempQueue.dequeue();
                studentQueue.enqueue(student);
            }

            if (!studentRemoved) {
                System.out.println();
                System.out.println("Student not found in team " + removeTeam.getTeamName() + "\n");
            }
        } else {
            System.out.println("Team " + removeTeam.getTeamName() + " not found.");
        }
    }

    @Override
    public void filterAssignmentTeams(String keyword, String group) {
        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>();
        System.out.println("All existing " + keyword + " teams: ");
        while (!Teams.isEmpty()) {
            TeamInfo currentTeam = Teams.dequeue();
            tempQueue.enqueue(currentTeam);
            if(currentTeam.getGroup().equals(group)){
                if (currentTeam.getType().toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("+----------------------------------------+");
                    System.out.printf("| Programme: %-27s |\n", currentTeam.getProgramme());
                    System.out.printf("| Tutorial Group: %-22s |\n", currentTeam.getGroup());
                    System.out.printf("| Team Name: %-27s |\n", currentTeam.getTeamName());
                    System.out.printf("| Description: %-25s |\n", currentTeam.getDescription());
                    System.out.printf("| Maximum Team Members: %-16d |\n", currentTeam.getMaxNum());
                    System.out.println("+----------------------------------------+");
                    System.out.println();
                }
            }
        }

        while (!tempQueue.isEmpty()) {
            Teams.enqueue(tempQueue.dequeue());
        }
    }

    @Override
    public void listTeams(String group) {
        if (Teams.isEmpty()) {
            System.out.println();
            System.out.println("No teams found 1.\n");
        } else {
            System.out.println("List of Teams:");
            CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>(); // Temporary queue to hold teams for re-enqueueing

            while (!Teams.isEmpty()) {
                TeamInfo team = Teams.dequeue();
                tempQueue.enqueue(team);
                if(team.getGroup().equals(group)){
                System.out.println();
                System.out.println("+----------------------------------------+");
                System.out.printf("| Programme: %-27s |\n", team.getProgramme());
                System.out.printf("| Tutorial Group: %-22s |\n", team.getGroup());
                System.out.printf("| Course: %-30s |\n", team.getType());
                System.out.printf("| Team Name: %-27s |\n", team.getTeamName());
                System.out.printf("| Description: %-25s |\n", team.getDescription());
                System.out.printf("| Maximum Team Members: %-16d |\n", team.getMaxNum());
                System.out.println("+----------------------------------------+");
                System.out.println();
                }

            }

            // Re-enqueue the teams back to the main queue
            while (!tempQueue.isEmpty()) {
                Teams.enqueue(tempQueue.dequeue());
            }
        }
    }

    @Override
    public void listStudentsByAssignmentTeam(T team) {

        if(team != null){
            if (!team.getStudentQueue().isEmpty()) {
                
                System.out.println("Students in " + team.getProgramme() + team.getGroup() + team.getTeamName() + ":");
                CircularArrayQueue<students> studentQueue = team.getStudentQueue();
                CircularArrayQueue<students> tempQueue = new CircularArrayQueue<>();

            while (!studentQueue.isEmpty()) {
                students student = studentQueue.dequeue();
                tempQueue.enqueue(student);
                System.out.println("+----------------------------------------+");
                System.out.printf("| ID: %-34s |\n", student.getStudentID());
                System.out.printf("| Name: %-32s |\n", student.getName());
                System.out.printf("| Email: %-31s |\n", student.getEmail());
                System.out.printf("| Phone Number: %-24s |\n", student.getPhoneNumber());
                System.out.println("+----------------------------------------+");
            }
            System.out.println();

            // Re-enqueue the students back into the team's student queue
            while (!tempQueue.isEmpty()) {
                students student = tempQueue.dequeue();
                studentQueue.enqueue(student);
            }
        } else {
            System.out.println();
            System.out.println("No students in " + team.getTeamName() + ".\n");
            }
        }else{
            System.out.println("Team not found.");
        }
    }

    @Override
    public void generateReport(String group) {
        System.out.println("+--------------------------------+");
        System.out.println("| List of reports:               |");
        System.out.println("+--------------------------------+");
        System.out.println("| 1. Report 1                    |");
        System.out.println("| 2. Report 2                    |");
        System.out.println("| 3. Exit                        |");
        System.out.println("+--------------------------------+");
        System.out.println("Select the report u want to generate: ");
        Scanner scanner = new Scanner(System.in);
        int report = scanner.nextInt();

        switch (report) {
            case 1:
                
                generateGeneralReport(group);
                break;
            case 2:
                displayAssignmentTeamsByCourse(group);
                break;
            case 3:
                return;
            default:
                break;

        }
    }
    
    private void displayAssignmentTeamsByCourse(String group) {
        CustomHashMap<String, Integer> courseAssignmentCount = new CustomHashMap<>();
        // Count the number of assignments for each course
        while (!Teams.isEmpty()) {
            TeamInfo team = Teams.dequeue();
            String course = team.getType();
            if(team.getGroup().equals(group)){
                if (courseAssignmentCount.containsKey(course)) {
                    int count = courseAssignmentCount.get(course);
                    courseAssignmentCount.put(course, count + 1);
                } else {
                    courseAssignmentCount.put(course, 1);
                }
            }
            
        }
        // Display the report
        System.out.println("+----------------------------------------+");
        System.out.println("| Course         | Number of Assignments |");
        System.out.println("+----------------------------------------+");

        for (String course : courseAssignmentCount.keys()) {
            int count = courseAssignmentCount.get(course);
            System.out.printf("| %-14s | %-21d |\n", course, count);
        }
        System.out.println("+----------------------------------------+");
    }


    private void generateGeneralReport(String group) {
        if (Teams.isEmpty()) {
            System.out.println("No assignment teams to generate a report for.");
            return;
        }

        String course = selectCourse();

        System.out.println("Summary of Assignment Team Report for: " + course);
        System.out.println("+-----------------------------------------------+");
        System.out.printf("| %-30s | %-10s |\n", "Team Name", "Num Students");
        System.out.println("+-----------------------------------------------+");

        int count = 0;
        int totalStudents = 0;
        T teamWithMaxStudents = null;
        T teamWithMinStudents = null;

        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>();// Temporary queue to hold teams for re-enqueueing
        CircularArrayQueue<TeamInfo> maxQueue = new CircularArrayQueue<>();
        CircularArrayQueue<TeamInfo> minQueue = new CircularArrayQueue<>();

        while (!Teams.isEmpty()) {
            TeamInfo team = Teams.dequeue();
            // Enqueue the team into the temporary queue
            tempQueue.enqueue(team);
            if(team.getGroup().equals(group)){
                if (team.getType().equals(course)) {
                    count++;
                    int numStudents = team.getStudentQueue().size();
                    totalStudents += numStudents;

                    if (numStudents >= team.getMaxNum()) {
                        maxQueue.enqueue(team);
                    }

                    if (numStudents < team.getMaxNum()) {
                        minQueue.enqueue(team);
                    }

                    System.out.printf("| %-30s | %-12d |\n", team.getTeamName(), numStudents);
                }
            }
        }

        System.out.println("+-----------------------------------------------+");
        System.out.println();
        System.out.println("Summary:");
        System.out.println("Total Teams: " + count);
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Team that has full members: ");
        while (!maxQueue.isEmpty()) {
            TeamInfo max = maxQueue.dequeue();
            System.out.println(max.getTeamName() + "(" + max.getStudentQueue().size() + ")" + " ");
        }
        System.out.println("Team that haven't full: ");
        while (!minQueue.isEmpty()) {
            TeamInfo min = minQueue.dequeue();
            System.out.print(min.getTeamName() + "(" + min.getStudentQueue().size() + ")" + " ");
        }
        System.out.println();

        // Re-enqueue the teams back into the main queue
        while (!tempQueue.isEmpty()) {
            Teams.enqueue(tempQueue.dequeue());
        }
    }



    public TeamInfo findTeamByName(String prog, String group, String course, String teamNameToFind) {
        TeamInfo teamMatched = null; // Initialize to null
        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>();
        while (!Teams.isEmpty()) {
            TeamInfo currentTeam = Teams.dequeue();
            tempQueue.enqueue(currentTeam);
            if (currentTeam.getProgramme().equals(prog) 
                    && currentTeam.getGroup().equals(group) 
                    && currentTeam.getType().equals(course) 
                    && currentTeam.getTeamName().equals(teamNameToFind)) {
                teamMatched = currentTeam; // Assign the matched team
                break; // No need to continue searching if found
            }
        }

        while (!tempQueue.isEmpty()) {
            Teams.enqueue(tempQueue.dequeue());
        }

        return teamMatched; // Return the matched team (or null if not found)
    }

    private boolean containTeam(String group, String course, String name) {
        boolean found = false;
        CircularArrayQueue<TeamInfo> tempQueue = new CircularArrayQueue<>();
        while (!Teams.isEmpty()) {
            TeamInfo team = Teams.dequeue();
            tempQueue.enqueue(team);
            if(team.getGroup().equals(group)){
                if (team.getType().equals(course)) {
                    if (team.getTeamName().equals(name)) {
                        System.out.println();
                        found = true;
                        break;
                    }
                }
            }
        }
        while (!tempQueue.isEmpty()) {
            Teams.enqueue(tempQueue.dequeue());
        }
        return found;
    }
    
    private boolean containStud(students student, TeamInfo team){
        boolean found = false;
        CircularArrayQueue<students> tempQueue = new CircularArrayQueue<>();
        
        while (!team.getStudentQueue().isEmpty()) {
            students stud = team.getStudentQueue().dequeue();
            tempQueue.enqueue(stud);
            if(stud.getStudentID().equals(student.getStudentID())){
                found = true;
                System.out.println("+----------------------------------------+");
                System.out.printf("| ID: %-34s |\n", stud.getStudentID());
                System.out.printf("| Name: %-32s |\n", stud.getName());
                System.out.printf("| Email: %-31s |\n", stud.getEmail());
                System.out.printf("| Phone Number: %-24s |\n", stud.getPhoneNumber());
                System.out.println("+----------------------------------------+");
                System.out.println(student.getName() + " already existed in " + team.getTeamName());
                System.out.println();
                break;
            }
        }
        while (!tempQueue.isEmpty()) {
            team.getStudentQueue().enqueue(tempQueue.dequeue());
        }
        return found;
    }

    public String selectCourse() {
        int num;
        String teamType = null;
        do {
            num = 0;
            System.out.println();
            System.out.print("|------------------------------------------|\n");
            System.out.print("| Available ourse:                         |\n");
            System.out.print("|------------------------------------------|\n");
            System.out.print("| 1. Data Structure Analysis.              |\n");
            System.out.print("| 2. Software Engineering.                 |\n");
            System.out.print("| 3. Human Computer Interaction.           |\n");
            System.out.print("| 4. Research Method.                      |\n");
            System.out.print("| 5. Object-Oriented Analysis and Design.  |\n");
            System.out.print("| 6. Exit.                                 |\n");
            System.out.print("|------------------------------------------|\n");

            System.out.print("Select the course: ");
            Scanner scanner = new Scanner(System.in);
            String type = scanner.nextLine();
            num = Integer.parseInt(type);
        } while (num < 1 || num > 6);

        switch (num) {
            case 1:
                teamType = "DSA";
                break;
            case 2:
                teamType = "SE";
                break;
            case 3:
                teamType = "HCI";
                break;
            case 4:
                teamType = "RM";
                break;
            case 5:
                teamType = "OOAD";
                break;
            case 6:
                break;
        }
        return teamType;
    }
    
    public TeamInfo enterTeamDetail(String programme, String group){
        //Select course
        String teamType = selectCourse();
        //Display specific course's team
        filterAssignmentTeams(teamType, group);
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        System.out.print("Enter team description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter maximum team members: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        // Create a TeamInfo object using the provided teamName and desc
        TeamInfo newTeam = new TeamInfo(programme, group, teamType ,teamName, desc, num);
        return newTeam;
    }
    
    public void enterStudentDetail(students selectedStudent, String prog, String group){
            if (selectedStudent != null) {
                String Course = selectCourse();
                filterAssignmentTeams(Course, group);

                System.out.print("Enter team name: ");
                String teamToAdd = scanner.nextLine();
                
                TeamInfo found = findTeamByName(prog, group, Course,teamToAdd); // Retrieve the team using the teamName
                
                if (found != null) {
                            addStudentToTeam(selectedStudent, found);
                        } else {
                            System.out.println("Team not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                }
    }
    
}
    
