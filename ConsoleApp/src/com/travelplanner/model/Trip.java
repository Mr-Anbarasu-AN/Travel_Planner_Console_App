package com.travelplanner.model;


public class Trip {
	// Encapsulation
	//the data members are declared as private 
	//and allows these data members to be accessed only through getter and setter methods
    private int tripId;
    private int userId;
    private String destination;
    private String accommodation;
    private String transport;
    private String schedule;

    public Trip() {
    }

    public Trip(int tripId, int userId, String destination, String accommodation, String transport, String schedule) {
        this.tripId = tripId;
        this.userId = userId;
        this.destination = destination;
        this.accommodation = accommodation;
        this.transport = transport;
        this.schedule = schedule;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }


    //Polymorphism
    // By using method overloading or method overriding
    @Override
    public String toString() {
        return "Trip [tripId=" + tripId + ", userId=" + userId + ", destination=" + destination + ", accommodation="
                + accommodation + ", transport=" + transport + ", schedule=" + schedule + "]";
    }
}




/*
package com.travelplanner.model;
 

public class Trip {
    private int tripId;
    private String destination;
    private String accommodation;
    private String transport;
    private String schedule;
    private int userId; // Foreign key referencing User table
    
    
    public Trip() {
    	
    }
    
    public Trip(int tripId, String destination, String accommodation, String transport, String schedule, int userId) {
		super();
		this.tripId = tripId;
		this.destination = destination;
		this.accommodation = accommodation;
		this.transport = transport;
		this.schedule = schedule;
		this.userId = userId;
	}

	// Getters and setters

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // toString() method

    @Override
    public String toString() {
        return String.format("Trip [tripId=%s, destination=%s, accommodation=%s, transport=%s, schedule=%s, userId=%s]",
                tripId, destination, accommodation, transport, schedule, userId);
    }
}

*/
