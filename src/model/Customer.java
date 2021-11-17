package model;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    //Constructor
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, invalid Email!");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) obj;
        return Objects.equals(firstName, customer.firstName) &&
        Objects.equals(lastName, customer.lastName) &&
        Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "FirstName: " + firstName + " LastName: " + lastName + " Email: " + email;
    }
}