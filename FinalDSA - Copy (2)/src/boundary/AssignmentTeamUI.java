/*
Author: Oo Kai Jie
 */
package boundary;

import adt.ArrayList;
import adt.CustomHashMap;
import entity.TeamInfo;
import entity.students;
import entity.TutorialGroups;
import adt.TutorialGroupManagementInterface;
import adt.TutorialGroupManagementInterface;
import dao.TutorialGroupDAO;
import dao.AssignmentTeam;
import dao.ProgrammeManager;
import entity.History;
import entity.Programme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AssignmentTeamUI {
    
        private static ProgrammeManager<Programme, TutorialGroups> myList = new ProgrammeManager<>();
    private static ArrayList<Programme> programmeList = myList.getProgrammes();
    
    
    

    public static void AssignmentTeamUI() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String timestamp = dateFormat.format(currentDate);

        Scanner scanner = new Scanner(System.in);

//        Programme prog1 = new Programme("RSW", "Software Engineering", "FOCS");
//        Programme prog2 = new Programme("RSD", "Software Development", "FOCS");

//        TutorialGroups group1 = new TutorialGroups("G1", "RSD", new CustomHashMap<>(), 0, 35);
//        TutorialGroups group2 = new TutorialGroups("G2", "RSW", new CustomHashMap<>(), 0, 35);
//
////        myList.addProgramme(prog1);
////        myList.addProgramme(prog2);
//
////        myList.addTutorialGroupToProgramme(group2, "RSD");
////        myList.addTutorialGroupToProgramme(group1, "RSD");
//
//        TeamInfo DSAteam1 = new TeamInfo("RSD", "G1", "DSA", "T1", "testing", 5);
//        TeamInfo DSAteam2 = new TeamInfo("RSD", "G2", "DSA", "T2", "testing", 5);
//
//        TeamInfo OOADteam1 = new TeamInfo("RSD", "G1", "OOAD", "T1", "testing", 5);
//        TeamInfo OOADteam2 = new TeamInfo("RSD", "G2", "OOAD", "T2", "testing", 5);

//        students student1 = new students("23PMR01234", "John", "RSD", "G1", "john@gmail.com", "0123456789", "123456789");

//        History history1 = new History(timestamp, "Student Added", student1.getName() + "" + "(" + student1.getStudentID() + ")", student1.getTutorialGroup());

        //Select programme & tutorial groups
        Programme prog = myList.selectProgramme();
        TutorialGroupManagementInterface<students, TutorialGroups, History> tutorialGroupStud = new TutorialGroupDAO<>(myList);
        ArrayList<TutorialGroups> test = prog.getTutorialGroups();
        tutorialGroupStud.displayTutorialGroups(prog);
        System.out.println("Select tutorial group: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        TutorialGroups group = tutorialGroupStud.selectTutorialGroup(prog, index);
        CustomHashMap<String, students> stud = group.getStudents();
        AssignmentTeam<TeamInfo> Team = new AssignmentTeam<>(group);

//        // Create a new student
//        students student3 = new students("23PMR01236", "Alice", "RSD", "G1", "alice@gmail.com", "0123456780", "9876543210");

//// Add the student to the TutorialGroups
//        group1.addStudent(student3);
//
////        tutorialGroupStud.addStudentToGroup(group1.getGroupID(), student1, history1);
////        tutorialGroupStud.addStudent(student1, history1);
////        tutorialGroupStud.addStudent(student2, history2);
////        tutorialGroupStud.addTutorialGroup(group2);
//        Team.createAssignmentTeam(DSAteam1);
//        Team.createAssignmentTeam(DSAteam2);
//
//        Team.createAssignmentTeam(OOADteam1);
//        Team.createAssignmentTeam(OOADteam2);
//
//        Team.addStudentToTeam(student1, DSAteam1);
//        Team.addStudentToTeam(student1, OOADteam1);

        while (true) {
            //Display menu
            System.out.println("Assignment Team Management Menu:");
            System.out.println("1. Create Assignment Team");
            System.out.println("2. Remove Assignment Team");
            System.out.println("3. Amend Assignment Team Details");
            System.out.println("4. Add Student to Assignment Team");
            System.out.println("5. Remove Student from Assignment Team");
            System.out.println("6. Filter Assignment Teams");
            System.out.println("7. List Assignment Teams");
            System.out.println("8. List Students under Assignment Team");
            System.out.println("9. Generate Reports");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            String choiceStr = scanner.nextLine();

            if (choiceStr.matches("\\d+")) {
                int choice = Integer.parseInt(choiceStr);

                switch (choice) {
                    case 1:
                        // Add new team                     
                        if (group != null) {
                            Team.createAssignmentTeam(Team.enterTeamDetail(group.getProgramme(), group.getGroupID()));
                        }
                        break;

                    case 2:
                        //Remove the team                 
                        String course = Team.selectCourse();

                        Team.filterAssignmentTeams(course, group.getGroupID());
                        System.out.println("Enter team name: ");
                        String name = scanner.nextLine();
                        Team.removeAssignmentTeam(Team.findTeamByName(prog.getProgrammeID(), group.getGroupID(), course, name));
                        break;

                    case 3:
                        //Modify the team description                    
                        Team.amendAssignmentTeam(Team.enterTeamDetail(prog.getProgrammeID(), group.getGroupID()));
                        break;

                    case 4:
                        //Add students to assignment team        
                        tutorialGroupStud.listAllStudentsInSelectedGroup(stud);
                        students selectedStudent = tutorialGroupStud.selectStudentInGroup(group);
                        Team.enterStudentDetail(selectedStudent, prog.getProgrammeID(), group.getGroupID());
                        break;

                    case 5:
                        //Remove student from assignment team                  
                        course = Team.selectCourse();
                        Team.filterAssignmentTeams(course, group.getGroupID());

                        System.out.println("Enter team name: ");
                        name = scanner.nextLine();
                        Team.removeStudentFromTeam(Team.findTeamByName(prog.getProgrammeID(), group.getGroupID(), course, name));

                        break;

                    case 6:
                        //Filter assignment team based on course
                        String keyword = Team.selectCourse();
                        Team.filterAssignmentTeams(keyword, group.getGroupID());
                        break;

                    case 7:
                        //List all assignment teams
                        Team.listTeams(group.getGroupID());
                        break;

                    case 8:
                        //List students by assignment team
                        course = Team.selectCourse();
                        Team.filterAssignmentTeams(course, group.getGroupID());
                        System.out.print("Enter team name: ");
                        String team = scanner.nextLine();
                        TeamInfo selectedTeam = Team.findTeamByName(prog.getProgrammeID(), group.getGroupID(), course, team);
                        Team.listStudentsByAssignmentTeam(selectedTeam);

                        break;

                    case 9:
                        // Generate report
                        Team.generateReport(group.getGroupID());
                        break;

                    case 0:
                        //Exit program
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        
    }
    
    public AssignmentTeamUI(ProgrammeManager<Programme, TutorialGroups> programmeList) {
    this.myList = programmeList;
}
    
    

}
