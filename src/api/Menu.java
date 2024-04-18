package api;

import java.util.*;

public interface Menu {
    void prompt(Scanner scanner);

    default void printMenuItems(String[] menuItems) {
        printLine();
        for(int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + ". " + menuItems[i]);
        }
        printLine();
    }
    default void printLine() {
        for(int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
