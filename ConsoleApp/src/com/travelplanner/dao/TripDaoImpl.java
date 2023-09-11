package com.travelplanner.dao;

import com.travelplanner.model.Trip;
import com.travelplanner.util.DBHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripDaoImpl implements TripDao {
    public void createTrip(Trip trip) {
    	
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBHandler.getConnection();
            String query = "INSERT INTO trip (destination, accommodation, transport, schedule, userid) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, trip.getDestination());
            ps.setString(2, trip.getAccommodation());
            ps.setString(3, trip.getTransport());
            ps.setString(4, trip.getSchedule());
            ps.setInt(5, trip.getUserId());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    trip.setTripId(generatedId);
                    System.out.println("Trip with ID " + generatedId + " created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating the trip: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    public Trip getTripById(int tripId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Trip trip = null;
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM trip WHERE tripid = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, tripId);
            rs = ps.executeQuery();
            if (rs.next()) {
                trip = extractTripFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the trip: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return trip;
    }

    public List<Trip> getTripsByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Trip> tripList = new ArrayList<>();
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM trip WHERE userid = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Trip trip = extractTripFromResultSet(rs);
                tripList.add(trip);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the trips: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return tripList;
    }

    public void updateTrip(Trip trip) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "UPDATE trip SET destination = ?, accommodation = ?, transport = ?, schedule = ? WHERE tripid = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, trip.getDestination());
            ps.setString(2, trip.getAccommodation());
            ps.setString(3, trip.getTransport());
            ps.setString(4, trip.getSchedule());
            ps.setInt(5, trip.getTripId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Trip with ID " + trip.getTripId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the trip: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    public void deleteTrip(int tripId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "DELETE FROM trip WHERE tripid = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, tripId);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Trip with ID " + tripId + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the trip: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    private Trip extractTripFromResultSet(ResultSet rs) throws SQLException {
        Trip trip = new Trip();
        trip.setTripId(rs.getInt("tripid"));
        trip.setDestination(rs.getString("destination"));
        trip.setAccommodation(rs.getString("accommodation"));
        trip.setTransport(rs.getString("transport"));
        trip.setSchedule(rs.getString("schedule"));
        trip.setUserId(rs.getInt("userid"));
        return trip;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while closing resources: " + e.getMessage());
        }
    }
}
