package service;

import model.*;
import java.util.*;

public final class CustomerService {
    private static Map<String, Customer> customers = new TreeMap<>();
    private static CustomerService INSTANCE;

    private CustomerService() {

    }

    public static CustomerService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    public static void addCustomer(Customer customer) {
        if(!customers.containsKey(customer.getEmail())) {
            customers.put(customer.getEmail(), customer);
        }
    }

    public static Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public static Collection<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
}

