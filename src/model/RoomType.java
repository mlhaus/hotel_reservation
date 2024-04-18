package model;

import utilities.*;

public enum RoomType {
    SINGLE("Single bed"), // Calls a constructor
    DOUBLE("Double bed") // Calls a constructor
    ; // semicolon needed when fields / methods follow

    private final String description;

    private RoomType(String description) {
        if(!Helpers.validString(description)) {
            throw new IllegalArgumentException("Description required");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
