package com.travelplanner.model;

public class User {
	// Encapsulation
	//the data members are declared as private 
	//and allows these data members to be accessed only through getter and setter methods

    private int userId;
    private String fullName;
    private String email;
    private long phone;
    private String password;
    
    //Polymorphism
    // By using method overloading or method overriding
    @Override
    public String toString() {
        return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + "]";
    }

    public User() {
    }

    public User(int userId, String fullName, String email, long phone, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}





/*
package com.travelplanner.model;

public class User {
    private int userId;
    private String fullName;
    private String email;
    private long phone;
    private String password;

    // Getters and setters
    
    

    public int getUserId() {
        return userId;
    }

    public User() {
    	
    }
    
    public User(int userId, String fullName, String email, int phone, String password) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() method

    @Override
    public String toString() {
        return String.format("User [userId=%s, fullName=%s, email=%s, phone=%s, password=%s]",
                userId, fullName, email, phone, password);
    }
}


*/
