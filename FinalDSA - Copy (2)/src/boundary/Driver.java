package boundary;

import adt.ArrayList;
import adt.TutorInterface;
import adt.TutorialGroupManagementInterface;
import adt.tutorLinkedList;
import static boundary.AssignmentTeamUI.AssignmentTeamUI;
import static boundary.ProgrammeUI.ProgrammeUI;
import static boundary.TutorialGroupMaintenanceUI.TutorialGroupMaintenanceUI;
import dao.ProgrammeManager;
import dao.TutorialGroupDAO;
import entity.History;
import entity.Programme;
import entity.Tutor;
import entity.TutorialGroups;
import entity.students;
import entity.tutorController;
import java.util.Scanner;

/**
 *
 * @author AMD
 */
public class Driver {

    private static Scanner scanner = new Scanner(System.in);
    public static TutorInterface<Tutor> tutorList = new tutorLinkedList<>();
    public static tutorController tutorController = new tutorController(tutorList);

   

    public static void main(String[] args) {
        
     int choice;

        do {
            System.out.println("1. Programme Management");
            System.out.println("2. Tutorial Group Management");
            System.out.println("3. Assignment Team Management");
            System.out.println("4. Tutor management ");
            System.out.println("5. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ProgrammeUI();
                    break;
                case 2:
                    TutorialGroupMaintenanceUI();
                    break;
                case 3:
                    AssignmentTeamUI();
                    break;
                case 4:
                    TutorUI tutorUI = new TutorUI(tutorList);
                    tutorUI.run();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Input, Please Try Again");
                    break;
            }
        } while (choice != 5);

    }

}
