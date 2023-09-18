/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author Lim Yek Sean
 */
import java.util.Scanner;
import entity.tutorController;
import adt.TutorInterface;
import adt.tutorLinkedList;
import entity.Tutor;

public class TutorUI {
    private final TutorInterface<Tutor> tutorList;

    public TutorUI(TutorInterface<Tutor> tutorList) {
        this.tutorList = tutorList;
    }

    public void run() {
    Scanner scanner = new Scanner(System.in);
    int choice;

do {
        displayMenu();
        choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                tutorList.view();
                addTutor(scanner);
                break;
            case 2:
                tutorList.view();
                editTutorMenu(scanner);
                break;
            case 3:
                tutorList.view();
                break;
            case 4:
                tutorList.view();
                removeTutor(scanner);
                break;
            case 5:
                displayReport();
                break;
            case 6:
                System.out.println("Exiting the Tutor Management System.");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 6);

}


    private void displayMenu() {
        System.out.println("\nWelcome To TARUMT Tutor Management System");
        System.out.println("-----------------------------------------------------");
        System.out.println("[1] Add Tutor");
        System.out.println("[2] Edit Tutor");
        System.out.println("[3] View Tutor");
        System.out.println("[4] Remove Tutor");
        System.out.println("[5] Report");
        System.out.println("[6] Exit previous");
        System.out.print("Please Enter Your Selection: ");
    }

    private void addTutor(Scanner scanner) {
        String tutorID;
        double salaryRate;
        while(true){//check validation
        System.out.print("Enter Tutor ID: ");
        tutorID = scanner.nextLine();
        if (tutorID.isEmpty()) {
            System.out.println("Tutor ID cannot be empty. Please try again.");
        } else {
            break; // Exit the loop if a valid ID is provided
        }
        }
        System.out.print("Enter Tutor Name: ");
        String tutorName = scanner.nextLine();
        
        System.out.print("Enter Tutor Phone No: ");
        String phoneNo = scanner.nextLine();
        
        System.out.print("Enter Tutor Email: ");
        String email = scanner.nextLine();
        
        while (true) {
        System.out.print("Enter Tutor Salary Rate: RM");
        if (scanner.hasNextDouble()) {
            salaryRate = scanner.nextDouble();

            // Check if the salary rate is valid (e.g., positive)
            if (salaryRate >= 0) {
                break; // Exit the loop if a valid salary rate is provided
            } else {
                System.out.println("Salary Rate must be a non-negative number. Please try again.");
            }
        } else {
            System.out.println("Invalid input. Salary Rate must be a number. Please try again.");
            scanner.nextLine(); // Consume the invalid input
        }
        }
        tutorList.addTutor(new Tutor(tutorID, tutorName, phoneNo,email,salaryRate));
        System.out.println("Tutor added successfully!");
        scanner.nextLine(); // Consume the newline character
    }

    private void editTutorMenu(Scanner scanner) {
        System.out.print("Enter Tutor ID to edit: ");
        String tutorID = scanner.next();
        if (tutorList.checkExist(tutorID)) {
            displayEditMenu();
            int editChoice = scanner.nextInt();

            switch (editChoice) {
                case 1:
                    editTutorName(tutorID, scanner);
                    break;
                case 2:
                    editTutorPhone(tutorID, scanner);
                    break;
                case 3:
                    editTutorEmail(tutorID, scanner);
                    break;
                case 4:
                    editTutorSalary(tutorID, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Returning to the main menu.");
            }
        } else {
            System.out.println("Tutor not found!");
        }
    }

    private void displayEditMenu() {
        System.out.println("Edit Tutor:");
        System.out.println("[1] Edit Name");
        System.out.println("[2] Edit Phone Number");
        System.out.println("[3] Edit Email");
        System.out.println("[4] Edit Salary Rate");
        System.out.print("Please Enter Your Selection: ");
    }

    private void editTutorName(String tutorID, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter new name for the tutor: ");
        String newName = scanner.nextLine(); // Read the entire line for the name
        tutorList.editTutor(tutorID, newName, null,null,0);
        System.out.println("Tutor name edited successfully!");
    }

    private void editTutorEmail(String tutorID, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter new phone no for the tutor: ");
        String newPhoneNo = scanner.nextLine(); // Read the entire line for the phone number
        tutorList.editTutor(tutorID, null, newPhoneNo,null,0);
        System.out.println("Tutor phone number edited successfully!");
    }
    
        private void editTutorPhone(String tutorID, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter new email for the tutor: ");
        String newEmail = scanner.nextLine(); // Read the entire line for the phone number
        tutorList.editTutor(tutorID, null, null,newEmail,0);
        System.out.println("Tutor email edited successfully!");
    }
        
            private void editTutorSalary(String tutorID, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter new salary rate for the tutor: RM ");
        double newSalary = scanner.nextDouble(); // Read the entire line for the phone number
        tutorList.editTutor(tutorID, null, null,null,newSalary);
        System.out.println("Tutor phone number edited successfully!");
    }


    private void removeTutor(Scanner scanner) {
        System.out.print("Enter Tutor ID to remove: ");
        String tutorID = scanner.next();
        if (tutorList.checkExist(tutorID)) {
            tutorList.removeTutor(tutorID);
            System.out.println("Tutor removed successfully!");
        } else {
            System.out.println("Tutor not found!");
        }
    }
    
    private void displayReport(){
        tutorList.generateSalaryReport();
    }
}