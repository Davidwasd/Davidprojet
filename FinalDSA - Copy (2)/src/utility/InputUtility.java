/*
Author: Tneh Kai Qing
*/

package utility;

import java.util.Scanner;

public class InputUtility {

    private static Scanner scanner = new Scanner(System.in);

    public static String promptInput(String promptMessage) {
        System.out.print(promptMessage);
        return scanner.nextLine();
    }

    public static int promptIntInput(String message) {
        int userInput = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(message);

            try {
                userInput = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return userInput;
    }

    public static void displaySuccessMessage(String successMessage) {
        System.out.println("Success: " + successMessage);
    }

    public static void displayFailureMessage(String failureMessage) {
        System.out.println("Failure: " + failureMessage);
    }
}
