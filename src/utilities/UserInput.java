package utilities;

import java.text.*;
import java.util.*;

public class UserInput {

    public static int getInt(String prompt) {
        int value = 0; // Define a default value
        Scanner scanner = new Scanner(System.in);
        for (;;) {
            System.out.print(prompt + ": ");
            try {
                value = scanner.nextInt(); // Reads an integer value
                scanner.nextLine(); // Reads the enter key press
                break;
            } catch (InputMismatchException e) {
                Helpers.displayError("Invalid number");
            }
        }
        scanner.close();
        return value; // Return the value
    }

    public static double getDouble(String prompt) {
        double value = 0; // 1) Define a default value
        Scanner scanner = new Scanner(System.in); // 3) Instantiate a Scanner object
        for (;;) { // 7d) Keep asking the user for input until they enter a valid value
            System.out.print(prompt + ": "); // 5) Prompt the user for input
            try { // 7a) Use a try-catch statement to handle errors gracefully
                value = scanner.nextDouble(); // 6a) Read a double value
                scanner.nextLine(); // 6b) Read the enter key press
                break; // 7e) End the loop when they enter a valid value
            } catch (InputMismatchException e) { // 7b) Exception caused by calling the nextDouble method with invalid input
                Helpers.displayError("Invalid number"); // 7c) Display an error message
            }
        }
        scanner.close(); // 4) Close the Scanner object.
        return value; // 2) Return the value
    }

    public static String getString(String prompt) {
        String value = ""; // 1) Define a default value
        Scanner scanner = new Scanner(System.in); // 3) Instantiate a Scanner object
        System.out.print(prompt + ": "); // 5) Prompt the user for input
        value = scanner.nextLine(); // 6) Read a string and the enter key press
        value = value.trim(); // 7) Format the input by removing any leading or trailing whitespace
        scanner.close(); // 4) Close the Scanner object.
        return value; // 2) Return the value
    }

    public static String getString(Scanner scanner, String prompt) {
        System.out.print(prompt + ": ");
        String value = scanner.nextLine().trim();
        return value;
    }

    public static boolean getBoolean(Scanner scanner, String prompt) {
        String value = "";
        do {
            System.out.print(prompt + " [Yes, No]: ");
            value = scanner.nextLine().trim();
        } while (!value.equalsIgnoreCase("Yes")
                && !value.equalsIgnoreCase("No")
                && !value.equalsIgnoreCase("Y")
                && !value.equalsIgnoreCase("N"));
        return value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("Y");
    }

    public static int getInt(Scanner scanner, String prompt) {
        return getInt(scanner, prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInt(Scanner scanner, String prompt, int min) {
        return getInt(scanner, prompt, min, Integer.MAX_VALUE);
    }

    public static int getInt(Scanner scanner, String prompt, int min, int max) {
        int value = 0;
        while (true) {
            System.out.print(prompt);
            if(min == Integer.MIN_VALUE && max == Integer.MAX_VALUE) {
                System.out.print(": ");
            } else if(min != Integer.MIN_VALUE && max == Integer.MAX_VALUE) {
                System.out.print(" [minimum " + min + "]: ");
            } else {
                System.out.print(" [between " + min + " and " + max + "]: ");
            }
            try {
                String valueStr = scanner.nextLine();
                value = Integer.parseInt(valueStr);
                if (value < min) {
                    Helpers.displayMessage("Value entered is too low");
                } else if(value > max) {
                    Helpers.displayMessage("Value entered is too high");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                Helpers.displayMessage("Invalid number");
            }
        }
        return value;
    }

    public static double getDouble(Scanner scanner, String prompt) {
        return getDouble(scanner, prompt, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static double getDouble(Scanner scanner, String prompt, int min) {
        return getDouble(scanner, prompt, min, Double.MAX_VALUE);
    }

    public static double getDouble(Scanner scanner, String prompt, double min, double max) {
        double value = 0;
        while (true) {
            System.out.print(prompt);
            if(min == Double.MIN_VALUE && max == Double.MAX_VALUE) {
                System.out.print(": ");
            } else if(min != Double.MIN_VALUE && max == Double.MAX_VALUE) {
                System.out.print(" [minimum " + min + "]: ");
            } else {
                System.out.print(" [between " + min + " and " + max + "]: ");
            }
            try {
                String valueStr = scanner.nextLine();
                value = Double.parseDouble(valueStr);
                if (value < min) {
                    Helpers.displayMessage("Value entered is too low");
                } else if(value > max) {
                    Helpers.displayMessage("Value entered is too high");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                Helpers.displayMessage("Invalid number");
            }
        }
        return value;
    }

    public static Date getDate(Scanner scanner, String prompt) {
        Date userDate = null;
        while (true) {
            System.out.print(prompt + " [mm/dd/yyyy]: ");
            String dateStr = scanner.nextLine().trim();
            try {
                userDate = Helpers.dateFormatFromUser.parse(dateStr);
                break;
            } catch (ParseException e1) {
                Helpers.displayMessage("Invalid date format");
            }
        }
        return userDate;
    }

    public static String getEmail(Scanner scanner) {
        String value = "";
        while (true) {
            System.out.print("Email address [example@domain.com]: ");
            value = scanner.nextLine().trim();
            if(Helpers.validEmail(value)) {
                break;
            } else {
                Helpers.displayMessage("Invalid email");
            }
        }
        return value;
    }
}
