package com.travelplanner.dao;

import com.travelplanner.model.Trip;

import java.util.List;

//Abstraction
// Defining a set of methods without specifying their implementations
public interface TripDao {
    public void createTrip(Trip trip);
    public Trip getTripById(int tripId);
    public List<Trip> getTripsByUserId(int userId);
    public void updateTrip(Trip trip);
    public void deleteTrip(int tripId);
}
