package utilities;

import java.time.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;

public class Helpers {

    /**
     * Generate a random integer in a range
     * @param min the minimum integer to randomly generate
     * @param max the maximum integer to randomly generate
     * @return the randomly generated integer
     */
    public static int randint(int min, int max) {
//        return (int)(Math.random() * (max - min + 1)) + min;
        return new Random().nextInt(max - min + 1) + min;
    }
    public static SimpleDateFormat dateFormatFromUser = new SimpleDateFormat("M/d/yyyy");
    public static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("EEE MMM d, yyyy");

    public static String formatDate(Date date) {
        return dateOutputFormat.format(date);
    }

    public static boolean areDatesInOrder(Date date1, Date date2) {
        return date1.compareTo(date2) < 0;
    }

    public static boolean isDateInTheFuture(Date date) {
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date.equals(today) || date.after(today);
    }

    public static void displayError(String message) {
        System.err.printf("*** ERROR: %s ***\n", message);
    }

    public static void displayMessage(String message) {
        System.out.println("*** " + message + " ***");
    }

    public static void pressEnterToContinue(Scanner scanner) {
        UserInput.getString(scanner, "Press enter to continue");
    }

    public static String toCurrency(double amt) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amt);
    }

    public static boolean validString(String str) {
        return str != null && !str.equals("");
    }

    public static boolean isNumeric(String str) {
        return str.matches("[0-9]+");
    }

    public static boolean validEmail(String email) throws IllegalArgumentException {
        // https://regexr.com/2rhq7
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static Date now() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date addDays(Date date, int numDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numDays);
        return calendar.getTime();
    }

}
