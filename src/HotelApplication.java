import api.*;
import utilities.*;
import java.util.*;

public class HotelApplication {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.prompt(scanner);
            Helpers.displayMessage("Good bye!");
        }
    }
}