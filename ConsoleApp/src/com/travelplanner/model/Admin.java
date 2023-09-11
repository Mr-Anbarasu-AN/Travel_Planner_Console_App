package com.travelplanner.model;

//Inheritance
//Admin inherits all the properties and methods from the User class
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(int userId, String fullName, String email, long phone, String password) {
        super(userId, fullName, email, phone, password);
    }
}
