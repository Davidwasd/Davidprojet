// AUTHOR : ONG CHUN ZHAO
package boundary;

import utility.InputValidator;
import java.util.Scanner;
import entity.*;
import adt.*;
import dao.*;

import java.util.Scanner;
import utility.InputUtility;

public class ProgrammeUI {

    public static void ProgrammeUI() {
        ProgrammeManager<Programme, TutorialGroups> myList = new ProgrammeManager<>();
        Scanner scanner = new Scanner(System.in);
        
        TutorialGroupMaintenanceUI tutorialUI = new TutorialGroupMaintenanceUI(myList);
        
        AssignmentTeamUI AssignmentTeam = new AssignmentTeamUI(myList);
        
       TutorialGroupDAO TutorialGroup = new TutorialGroupDAO(myList);
        
        

        Programme prog1 = new Programme("RSW", "Software Engineering", "FOCS");
        Programme prog2 = new Programme("RSD", "Software Development", "FOCS");
        Programme prog3 = new Programme("DFT", "Information Technology", "FOCS");
        Programme prog4 = new Programme("DCD", "Computer Science", "FOCS");
        Programme prog5 = new Programme("DCW", "Computer Science", "FOCS");
        Programme prog6 = new Programme("DCT", "Computer Science", "FOAD");
        Programme prog7 = new Programme("DCH", "Computer Science", "FOAD");

        TutorialGroups group1 = new TutorialGroups("G1", "RSW", new CustomHashMap<>(), 0, 35);
        TutorialGroups group2 = new TutorialGroups("G2", "RSD", new CustomHashMap<>(), 0, 35);

        myList.addProgramme(prog1);
        myList.addProgramme(prog2);
        myList.addProgramme(prog3);
        myList.addProgramme(prog4);
        myList.addProgramme(prog5);
//        myList.addProgramme(prog6);
//        myList.addProgramme(prog7);
//
        myList.addTutorialGroupToProgramme(group1, "RSW");
        myList.addTutorialGroupToProgramme(group2, "RSW");

        while (true) {
            System.out.println("|-----------------------------------------------|");
            System.out.println("|    ~~~~~~~Program Management System~~~~~~~    |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("|                                               |");
            System.out.println("|   1. Add Programme                            |");
            System.out.println("|   2. Delete Programme                         |");
            System.out.println("|   3. Search Programme                         |");
            System.out.println("|   4. Amend Programme                          |");
            System.out.println("|   5. Display All Programmes                   |");
            System.out.println("|   6. Add Tutorial Group                       |");
            System.out.println("|   7. Remove Tutorial Group                    |");
            System.out.println("|   8. Display Tutorial Group In A Programme    |");
            System.out.println("|   9. Generate Relevant Report                 |");
            System.out.println("|   10. Extra Function                          |");
            System.out.println("|   11. Exit                                    |");
            System.out.println("|                                               |");
            System.out.println("|-----------------------------------------------|");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("|                                               |");
                    System.out.println("|1. Normal Insertion                            |");
                    System.out.println("|2. Add To A Specific Index                     |");
                    System.out.println("|3. Back                                        |");
                    System.out.println("|                                               |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println();
                    System.out.print("Enter your choice: ");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice1) {
                        case 1:
                            String programmeID;
                            boolean isValidID = false;
                            boolean doesExist = true;

                            do {
                                System.out.print("Enter programmeID: ");
                                programmeID = scanner.nextLine();
                                programmeID = programmeID.toUpperCase();

                                // Validate the ID using IDValidator
                                String validatedID = InputValidator.IDValidator(programmeID);

                                if (validatedID == null) {
                                    System.out.println("Invalid input. Please enter a 3-character ID with only letters.");
                                } else {
                                    if (validatedID != null) {
                                        doesExist = myList.programmeExist(validatedID);

                                        if (doesExist) {
                                            System.out.println("ProgrammeID already exists. Please enter a different one.");
                                        } else {
                                            isValidID = true; // Both conditions met, proceed1
                                        }
                                    }
                                }
                            } while (!isValidID || doesExist);

                            String programmeName;
                            String validatedProgrammeName;

                            do {
                                System.out.print("Enter Programme Name: ");
                                programmeName = scanner.nextLine();
                                programmeName = programmeName.toUpperCase();
                                validatedProgrammeName = InputValidator.NameValidator(programmeName);

                                if (validatedProgrammeName == null) {
                                    System.out.println("Invalid Programme Name. Please enter again.");
                                }
                            } while (validatedProgrammeName == null);

                            String programmeFac;
                            String validatedProgrammeFac;
                            do {
                                System.out.print("Enter programmeFaculty: ");
                                programmeFac = scanner.nextLine();
                                programmeFac = programmeFac.toUpperCase();
                                validatedProgrammeFac = InputValidator.FacValidator(programmeFac);

                                if (validatedProgrammeFac == null) {
                                    System.out.println("Invalid faculty code. Please enter a 4-letter alphabetic code.");
                                }
                            } while (validatedProgrammeFac == null);

                            Programme newProgramme = new Programme(programmeID, programmeName, programmeFac);
                            myList.addProgramme(newProgramme);

                            break;

                        case 2:
                            String programmeID2;
                            boolean isValidID2 = false;
                            boolean doesExist2 = true;

                            System.out.println("Enter the index you want to insert to : ");
                            int index = scanner.nextInt();
                            scanner.nextLine();

                            do {
                                System.out.print("Enter programmeID: ");
                                programmeID2 = scanner.nextLine();
                                programmeID2 = programmeID2.toUpperCase();

                                // Validate the ID using IDValidator
                                String validatedID2 = InputValidator.IDValidator(programmeID2);

                                if (validatedID2 == null) {
                                    System.out.println("Invalid input. Please enter a 3-character ID with only letters.");
                                } else {
                                    if (validatedID2 != null) {
                                        doesExist2 = myList.programmeExist(validatedID2);

                                        if (doesExist2) {
                                            System.out.println("ProgrammeID already exists. Please enter a different one.");
                                        } else {
                                            isValidID2 = true;
                                        }
                                    }
                                }
                            } while (!isValidID2 || doesExist2);

                            System.out.print("Enter programmeName: ");
                            String programmeName2 = scanner.nextLine();

                            System.out.print("Enter programmeFaculty: ");
                            String programmeFac2 = scanner.nextLine();
                            programmeFac2 = programmeFac2.toUpperCase();

                            Programme newProgrammes = new Programme(programmeID2, programmeName2, programmeFac2);

                            myList.addProgrammeIndex(index, newProgrammes);
                            break;

                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("|                                               |");
                    System.out.println("|1. Delete A Specific Programme                 |");
                    System.out.println("|2. Delete All Programme                        |");
                    System.out.println("|3. Delete A Range Of Index                     |");
                    System.out.println("|4. Back                                        |");
                    System.out.println("|                                               |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println();
                    System.out.print("Enter your choice: ");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice2) {
                        case 1:
                            System.out.println("Enter the ProgramID of the Programme ; ");
                            String deleteID = scanner.nextLine();
                            deleteID = deleteID.toUpperCase();
                            myList.removeProgramme(deleteID);
                            break;
                        case 2:
                            myList.removeAllProgramme();
                            break;
                        case 3:
                            myList.displayProgramme();
                            System.out.println("Enter the Start Index : ");
                            int firstIndex = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter the Last Index : ");
                            int lastIndex = scanner.nextInt();
                            scanner.nextLine();
                            myList.removeProgrammeRange(firstIndex, lastIndex);
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;

                case 3:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("|                                               |");
                    System.out.println("| 1. Search With The Programme ID               |");
                    System.out.println("| 2. Search With The Index                      |");
                    System.out.println("| 3. Back                                       |");
                    System.out.println("|                                               |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println();
                    System.out.println("Enter Your Choice");
                    int choice3 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice3) {
                        case 1:
                            String searchID;
                            do {
                                System.out.println("Enter the ProgrammeID you wish to search : ");
                                searchID = scanner.nextLine();
                                if (InputValidator.IDValidator(searchID) == null) {
                                    System.out.println("Invalid input. Please enter a 3-character ID.");
                                }
                            } while (InputValidator.IDValidator(searchID) == null);
                            myList.searchProgramme(searchID);
                            break;
                        case 2:
                            System.out.println("Enter the index ");
                            int searchID6 = scanner.nextInt();
                            myList.displayIndexProgramme(searchID6);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                            break;
                    }
                    break;

                case 4:
                    System.out.println("Enter the ProgrammeID you wish to ammend : ");
                    String searchID2 = scanner.nextLine();
                    searchID2 = searchID2.toUpperCase();
                    myList.ammendProgramme(searchID2);
                    break;

                case 5:
                    myList.displayProgramme();
                    break;

                case 6:
                    System.out.println("Which Programme You Would Like To Add Tutorial Group To? ");
                    String searchID3 = scanner.nextLine();
                    System.out.println("Enter The Group ID : ");
                    String groupID = scanner.nextLine();
                    groupID = groupID.toUpperCase();
                    String course = InputUtility.promptInput("Enter course: ");
                    int enrolledStudents = InputUtility.promptIntInput("Enter number of enrolled students: ");
                    int capacity = InputUtility.promptIntInput("Enter capacity: ");
                    TutorialGroups tut = new TutorialGroups(groupID, course, new CustomHashMap<>(), enrolledStudents, capacity);
                    myList.addTutorialGroupToProgramme(tut, searchID3);
                    break;

                case 7:
                    System.out.println("Which Programme You Would Like To Delete A Tutorial Group ? ");
                    String searchID4 = scanner.nextLine();
                    searchID4 = searchID4.toUpperCase();
                    System.out.println("Enter The Group ID : ");
                    String groupDel = scanner.nextLine();
                    groupDel = groupDel.toUpperCase();
                    myList.deleteTutorialGroupFromProgramme(groupDel, searchID4);
                    break;
                case 8:
                    System.out.println("Enter the Programme ID : ");
                    String programmeSearch = scanner.nextLine();
                    programmeSearch = programmeSearch.toUpperCase();
                    myList.displayTutorialGroups(programmeSearch);
                    break;
                case 9:
                    System.out.println("|-----------------------------------------------------|");
                    System.out.println("|                                                     |");
                    System.out.println("| 1. Report For Programme With Most Tutorial Groups   |");
                    System.out.println("| 2. Report For Group Programme By Faculty            |");
                    System.out.println("| 3. Back                                             |");
                    System.out.println("|                                                     |");
                    System.out.println("|-----------------------------------------------------|");
                    System.out.println("Enter Your Choice");
                    int choice9 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice9) {
                        case 1:
                            myList.mostTut();
                            break;
                        case 2:
                            myList.listProgramsByFaculty();
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                            break;

                    }

                    break;
                case 10:
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("|                                               |");
                    System.out.println("| 1. Trim The Size Of ArrayList                 |");
                    System.out.println("| 2. Reversing The ArrayList                    |");
                    System.out.println("| 3. Back                                       |");
                    System.out.println("|                                               |");
                    System.out.println("|-----------------------------------------------|");
                    System.out.println("Enter Your Choice");
                    int choice10 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice10) {
                        case 1:
                            myList.trimArray();
                            break;
                        case 2:
                            myList.reverseList();
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;
                case 11:
                    System.out.println("Exiting the Programme Management System.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

}
