// AUTHOR : ONG CHUN ZHAO
package utility;

/**
 *
 * @author User
 */
import java.util.Scanner;

public class InputValidator {

    private Scanner scanner;

    public InputValidator() {
        scanner = new Scanner(System.in);
    }

    public static String IDValidator(String userInput) {
        if (userInput.length() != 3) {
            return null; // Invalid input length
        }

        if (!userInput.matches("[A-Za-z]+")) {
            return null;
        }

        userInput = userInput.toUpperCase();

        return userInput;
    }

    public static String FacValidator(String userInput) {
        if (userInput.length() != 4) {
            return null; // Invalid input length
        }

        if (!userInput.matches("[A-Za-z]+")) {
            return null;
        }

        userInput = userInput.toUpperCase();

        return userInput;
    }

    public static String NameValidator(String userInput) {
        if (!userInput.matches("[A-Za-z ]+")) {
            return null;
        }

        userInput = userInput.toUpperCase();

        return userInput;
    }

}
