// AUTHOR : ONG CHUN ZHAO
package dao;

import adt.*;
import entity.*;
import java.util.InputMismatchException;
import utility.*;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ProgrammeManager<T extends Programme, E extends TutorialGroups> {

    private ArrayList<Programme> programmeList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void addProgramme(T newProgramme) {
        boolean added = programmeList.add(newProgramme);
        if (added) {
            System.out.println("Programme added successfully.");
        } else {
            System.out.println("Failed to add programme.");
        }
    }

    public void addProgrammeIndex(int index, T newProgrammes) {
        boolean added = programmeList.add(index, newProgrammes);
        if (added) {
            System.out.println("Programme added successfully.");
        } else {
            System.out.println("Failed to add programme.");
        }
    }

    public void removeAllProgramme() {
        programmeList.clear();
        System.out.println();
        System.out.println("All programmes are deleted");
    }

    public void removeProgramme(String deleteID) {
        boolean removeSuccess = false;
        System.out.println("Delete ID: " + deleteID);
        int numberOfEntries = programmeList.getNumberOfEntries();

        for (int i = numberOfEntries; i >= 0; i--) {
            Programme program = programmeList.getEntry(i); // Use the getEntry method

            if (program != null && program.getProgrammeID().equals(deleteID)) {
                programmeList.remove(i);
                removeSuccess = true;
            }
        }
        if (!removeSuccess) {
            System.out.println("No Such ProgrammeID, Please Retry");
        } else {
            System.out.println("Programme Successfully Deleted");
        }

    }

    public void removeProgrammeRange(int index, int index2) {

        index--;
        index2--;

        if (index >= 0 && index2 < programmeList.size() && index <= index2) {
            programmeList.removeRange(index, index2);
            System.out.println("Elements from index " + index + " to " + index2 + " (inclusive) removed successfully.");
        } else {
            System.out.println("Invalid range. Removal operation aborted.");
        }
    }

    public void displayProgramme() {
        if (!programmeList.isEmpty()) {
            int numberOfEntries = programmeList.size();
            System.out.println(numberOfEntries + " Programmes Were Added And Found");
            System.out.println();

            for (int i = 1; i <= programmeList.size(); i++) {
                System.out.println((i) + ". " + programmeList.getEntry(i));
            }
            System.out.println();
        } else {
            System.out.println("No Programme Was Found, Try To Add One");
        }

    }

    public void searchProgramme(String searchID) {
        searchID = searchID.toUpperCase();
        int numberOfEntries = programmeList.size();
        boolean found = false;

        try {
            for (int i = numberOfEntries; i >= 0; i--) {
                Programme program = programmeList.getEntry(i); // Use the getEntry method
                if (program != null && programmeList.getEntry(i).getProgrammeID().equals(searchID)) {
                    System.out.println(program.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Programme Not Found");

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.nextLine();
        }
    }

    public void displayIndexProgramme(int searchID) {
        if (searchID >= 0 && searchID <= programmeList.size()) {
            // Retrieve and display the object at the specified index
            Programme object = programmeList.getEntry(searchID);
            System.out.println("Object at index " + searchID + ": " + object);
        } else {
            System.out.println("Invalid index. Please enter a valid index.");
        }
    }

    public void ammendProgramme(String searchID) {
        Programme programToUpdate = null;
        int indexToUpdate = -1;

        // Search for the program by ID
        for (int i = 0; i <= programmeList.size(); i++) {
            Programme program = programmeList.getEntry(i);
            if (program != null && program.getProgrammeID().equals(searchID)) {
                programToUpdate = program;
                indexToUpdate = i;
                break;
            }
        }

        if (programToUpdate == null) {
            System.out.println("Programme Not Found");
            return;
        }

        System.out.println("Programme Found:");
        System.out.println(programToUpdate);

        System.out.println("Select an option to update:");
        System.out.println("1. Update Programme ID");
        System.out.println("2. Update Programme Name");
        System.out.println("3. Update Programme Faculty");
        System.out.println("4. Update All of Them");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                String newID;
                boolean isValidID = false;
                boolean doesExist = false;

                do {
                    System.out.print("Enter new Programme ID: ");
                    newID = scanner.nextLine();
                    newID = newID.toUpperCase();

                    String validatedID = InputValidator.IDValidator(newID);

                    if (validatedID == null) {
                        System.out.println("Invalid input. Please enter a 3-character ID with only letters.");
                    } else {
                        doesExist = programmeExist(newID);

                        if (doesExist) {
                            System.out.println("ProgrammeID already exists. Please enter a different one.");
                        } else {
                            isValidID = true;
                        }
                    }
                } while (!isValidID || doesExist);

                programToUpdate.setProgrammeID(newID);
                break;
            case 2:
                System.out.print("Enter new Programme Name: ");
                String newName = scanner.nextLine();
                programToUpdate.setProgrammeName(newName);
                break;
            case 3:
                System.out.print("Enter new Programme Faculty: ");
                String newFac = scanner.nextLine();
                newFac = newFac.toUpperCase();
                programToUpdate.setProgrammeFac(newFac);
                break;
            case 4:
                System.out.print("Enter new Programme ID: ");
                String newID2 = scanner.nextLine();
                newID2 = newID2.toUpperCase();
                programToUpdate.setProgrammeID(newID2);

                System.out.print("Enter new Programme Name: ");
                String newName2 = scanner.nextLine();
                programToUpdate.setProgrammeName(newName2);

                System.out.print("Enter new Programme Faculty: ");
                String newFac2 = scanner.nextLine();
                newFac2 = newFac2.toUpperCase();
                programToUpdate.setProgrammeFac(newFac2);

                Programme programme = new Programme(newID2, newName2, newFac2);
                programmeList.replace(indexToUpdate, programme);

                break;
            default:
                System.out.println("Invalid Option");
                break;
        }

        System.out.println("Programme Updated:");
        System.out.println(programToUpdate);
    }

    public boolean programmeExist(String programmeID) {
        if (programmeList == null) {
            return false;
        }

        for (int i = 0; i <= programmeList.size(); i++) {
            Programme program = programmeList.getEntry(i);
            if (program != null && programmeID.equals(program.getProgrammeID())) {
                return true;
            }
        }

        return false;
    }

    public void addTutorialGroupToProgramme(TutorialGroups tut, String searchID3) {
        searchID3 = searchID3.toUpperCase();
        Programme program = null;
        int numberOfEntries = programmeList.getNumberOfEntries();

        try {
            for (int i = numberOfEntries; i >= 0; i--) {
                if (programmeList.getEntry(i).getProgrammeID().equals(searchID3)) {
                    program = programmeList.getEntry(i);
                    break;
                }
            }

            if (program != null) {
                program.addTutorialGroupToProgramme(tut);
                System.out.println("Tutorial Group added to Program: " + program.getProgrammeName() + " With Programme ID " + program.getProgrammeID());
            }

        } catch (NullPointerException e) {
            System.out.println("Programe Not Found");
        }
    }

    public void deleteTutorialGroupFromProgramme(String groupDel, String searchID4) {
        Programme program = null;
        int numberOfEntries = programmeList.getNumberOfEntries();

        try {
            for (int i = numberOfEntries; i >= 0; i--) {
                if (programmeList.getEntry(i).getProgrammeID().equals(searchID4)) {
                    program = programmeList.getEntry(i);
                    break; // Exit the loop once the program is found
                }
            }

            if (program != null) {
                ArrayList<TutorialGroups> tutorialGroups = program.getTutorialGroups();
                if (tutorialGroups != null) {
                    for (int j = tutorialGroups.size(); j >= 0; j--) {
                        TutorialGroups tut = tutorialGroups.getEntry(j);
                        if (tut != null && groupDel.equals(tut.getGroupID())) {
                            tutorialGroups.remove(j); // Remove the matching TutorialGroup
                            System.out.println("Tutorial Group with ID " + groupDel + " deleted from Programme " + program.getProgrammeName());
                            return; // Exit the method once the TutorialGroup is deleted
                        }
                    }
                } else {
                    System.out.println("Tutorial Groups list for Programme " + program.getProgrammeName() + " With Programme ID " + program.getProgrammeID());
                }
            } else {
                System.out.println("Programme with ID " + searchID4 + " not found.");
            }

            // If the method reaches here, it means the TutorialGroup was not found.
            System.out.println("Tutorial Group with ID " + groupDel + " not found in Programme " + searchID4);
        } catch (NullPointerException e) {
            System.out.println("Programe Not Found");
        }
    }

    public void displayTutorialGroups(String programmeSearch) {
        boolean foundProgramme = false;
        int numberOfEntries = programmeList.getNumberOfEntries();

        for (int i = numberOfEntries; i >= 0; i--) {
            Programme program = programmeList.getEntry(i);

            if (program != null && programmeSearch.equals(program.getProgrammeID())) {
                foundProgramme = true;
                ArrayList<TutorialGroups> tutorialGroups = program.getTutorialGroups();

                System.out.println("Tutorial Groups for Programme " + program.getProgrammeName() + " With Programme ID " + program.getProgrammeID() + ":");
                System.out.println();

                if (tutorialGroups != null) {
                    for (int j = 0; j <= tutorialGroups.size(); j++) {
                        TutorialGroups tut = tutorialGroups.getEntry(j);
                        if (tut != null) {
                            System.out.println(tut.toString());
                            System.out.println();
                        }
                    }

                    if (tutorialGroups.isEmpty()) {
                        System.out.println("No Tutorial Groups found for Programme " + program.getProgrammeName() + " With Programme ID " + program.getProgrammeID());
                    }
                }
                break; // Exit the loop once the program is found and processed
            }
        }

        if (!foundProgramme) {
            System.out.println("Programme with ID " + programmeSearch + " not found.");
        }
    }

    public void trimArray() {
        int sizeBefore = programmeList.size();
        System.out.println("Size before trimToSize: " + sizeBefore);

        // Calling trimToSize to reduce capacity to size
        programmeList.trimToSize();

        // Check the size after trimming
        int sizeAfter = programmeList.size();
        System.out.println("Size after trimToSize: " + sizeAfter);

    }

    public void reverseList() {
        programmeList.reverse();
        for (int i = 1; i <= programmeList.size(); i++) {
            System.out.println((i) + ". " + programmeList.getEntry(i));
        }
    }

    public void mostTut() {
        if (programmeList.isEmpty()) {
            System.out.println("No programs available.");
            return;
        }

        int maxTutorialGroups = -1;

        // Find the maximum number of tutorial groups
        for (int i = 0; i < programmeList.size(); i++) {
            Programme programme = programmeList.getEntry(i);
            if (programme != null) {
                int tutorialGroupCount = programme.getTutorialGroups().size();
                maxTutorialGroups = Math.max(maxTutorialGroups, tutorialGroupCount);
            }
        }

        if (maxTutorialGroups == 0) {
            System.out.println("No tutorial groups added to any program.");
            return;
        }

        for (int i = 0; i < programmeList.size(); i++) {
            Programme programme = programmeList.getEntry(i);
            if (programme != null) {
                int tutorialGroupCount = programme.getTutorialGroups().size();
                if (tutorialGroupCount == maxTutorialGroups) {
                    System.out.println();
                    System.out.println();
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println("|                                                       |");
                    System.out.println("|                   Report Title                        |");
                    System.out.println("|       Programs with the Most Tutorial Groups          |");
                    System.out.println("|                                                       |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println("|                                                       |");
                    System.out.println("|                                                       |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println("                Program ID: " + programme.getProgrammeID());
                    System.out.println("                Program Name: " + programme.getProgrammeName());
                    System.out.println("                Number of Tutorial Groups: " + maxTutorialGroups);
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println();
                    System.out.println();
                }
            }
        }
    }

    public void listProgramsByFaculty() {
        if (programmeList.isEmpty()) {
            System.out.println("No programs available.");
            return;
        }

        ArrayList<String> faculties = new ArrayList<>();
        ArrayList<ArrayList<Programme>> programsByFaculty = new ArrayList<>();

        for (int i = 0; i <= programmeList.size(); i++) {
            Programme program = programmeList.getEntry(i);

            if (program == null) {
                continue; // Skip null programs
            }

            String faculty = program.getProgrammeFac();
            boolean facultyFound = false;

            for (int j = 0; j <= faculties.size(); j++) {
                String facultyEntry = faculties.getEntry(j);

                if (facultyEntry != null && facultyEntry.equals(faculty)) {
                    ArrayList<Programme> programsEntry = programsByFaculty.getEntry(j);

                    if (programsEntry != null) {
                        programsEntry.add(program);
                        facultyFound = true;
                        break;
                    }
                }
            }

            if (!facultyFound) {
                faculties.add(faculty);
                ArrayList<Programme> facultyPrograms = new ArrayList<>();
                facultyPrograms.add(program);
                programsByFaculty.add(facultyPrograms);
            }
        }

        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
        System.out.println("|                                                       |");
        System.out.println("|                   Report Title                        |");
        System.out.println("|           Programs Grouped By Faculty                 |");
        System.out.println("|                                                       |");
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");

        for (int i = 0; i <= faculties.size(); i++) {
            String faculty = faculties.getEntry(i);
            ArrayList<Programme> facultyPrograms = programsByFaculty.getEntry(i);

            if (facultyPrograms == null) {
                continue;
            }
            System.out.println("Faculty: " + faculty);
            System.out.println("Number of Programs: " + facultyPrograms.size());
            System.out.println("Program IDs:");
            for (int j = 0; j <= facultyPrograms.size(); j++) {
                Programme program = facultyPrograms.getEntry(j);

                if (program != null) {
                    System.out.println("  - " + program.getProgrammeID());
                }
            }
            System.out.println("--------------------------------------------");
        }
    }

    public ArrayList<Programme> getProgrammes() {
        return programmeList;
    }

    public Programme selectProgramme() {
        int numberOfEntries = programmeList.getNumberOfEntries();
        if (programmeList.isEmpty()) {
            System.out.println("No programmes available.");
            return null;
        }

        System.out.println("Select a programme:");
        System.out.println("");

        // Display the list of programmes
        for (int i = 1; i <= numberOfEntries; i++) {
            Programme programme = programmeList.getEntry(i);
            System.out.println((i) + ". " + programme.getProgrammeID() + " - " + programme.getProgrammeName());
        }

        int selectedProgrammeNumber = InputUtility.promptIntInput("Enter the number of the programme: ");
        System.out.println("");

        if (selectedProgrammeNumber >= 1 && selectedProgrammeNumber <= numberOfEntries) {
            Programme selectedProgramme = programmeList.getEntry(selectedProgrammeNumber);
            return selectedProgramme;
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }
    

}
