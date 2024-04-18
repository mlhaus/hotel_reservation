package model;

import service.*;
import utilities.*;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private String email;

    public Customer() {
        this("Undefined", "Undefined", "undefined@example.com");
    }

    public Customer(String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        if(!Helpers.validString(firstName)) {
            throw new IllegalArgumentException("First name required");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if(!Helpers.validString(lastName)) {
            throw new IllegalArgumentException("Last name required");
        }
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        if(!Helpers.validEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        } else if(CustomerService.getInstance().getCustomer(email) != null) {
            throw new IllegalArgumentException("That customer email already exists");
        }
        this.email = email;
    }

    public static void printCustomerHeader() {
        System.out.printf("%-20s%-20s%-20s\n", "First Name", "Last Name", "Email");
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-20s", firstName, lastName, email);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Customer)obj) == 0;
    }

    @Override
    public int compareTo(Customer o) {
        return this.email.compareToIgnoreCase(o.email);
    }
}
