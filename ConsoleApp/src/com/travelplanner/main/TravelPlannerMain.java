package com.travelplanner.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.travelplanner.model.Trip;
import com.travelplanner.model.User;
import com.travelplanner.model.Admin;
import com.travelplanner.dao.TripDao;
import com.travelplanner.dao.TripDaoImpl;
import com.travelplanner.dao.UserDao;
import com.travelplanner.dao.UserDaoImpl;

public class TravelPlannerMain {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDaoImpl();
        TripDao tripDao = new TripDaoImpl();
        List<Trip> tripList = new ArrayList<>();
        User currentUser = null;

        System.out.println("Travel Planner Application");

        while (currentUser == null) {
            System.out.println("1. Login \n2. Register\n3. Admin_Login");
            System.out.println("Enter choice");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser = login(scanner, userDao);
                    break;
                case 2:
                    currentUser = register(scanner, userDao);
                    break;
                case 3:
                    currentUser = adminLogin(scanner);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        if (currentUser instanceof Admin) {
            System.out.println("Welcome, Admin!");
            adminOperations(scanner, userDao, tripDao);
        } else {
            System.out.println("Welcome, " + currentUser.getFullName());
            userOperations(scanner, currentUser, tripDao);
        }

        scanner.close();
    }

    private static User login(Scanner scanner, UserDao userDao) {
        System.out.println("Enter your email:");
        String email = scanner.next();

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            System.out.println("User not found. Please register first.");
            return null;
        }

        System.out.println("Enter your password:");
        String password = scanner.next();

        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return null;
        }

        return user;
    }

    private static User register(Scanner scanner, UserDao userDao) {
        User user = new User();
        System.out.println("Enter your full name:");
        user.setFullName(scanner.next());
        scanner.nextLine();

        System.out.println("Enter your email:");
        user.setEmail(scanner.next());

        System.out.println("Enter your phone number:");
        try {
            user.setPhone(scanner.nextLong());
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input for phone number. Please enter a valid phone number.");
            scanner.nextLine();
            return null;
        }
        scanner.nextLine();

        System.out.println("Enter your password:");
        user.setPassword(scanner.next());

        userDao.createUser(user);
        System.out.println("Registration successful. Please login.");
        return null;
    }

    private static User adminLogin(Scanner scanner) {
        System.out.println("Admin Login");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return new Admin(-1, "Admin", "admin@example.com", 0, ADMIN_PASSWORD);
        } else {
            System.out.println("Invalid admin credentials. Please try again.");
            return null;
        }
    }

    private static void adminOperations(Scanner scanner, UserDao userDao, TripDao tripDao) {
        int ch;
        do {
            System.out.println("\nAdmin Operations");
            System.out.println("1. View All Users");
            System.out.println("2. View Users' Trips");
            System.out.println("3. Logout");
            System.out.println("Enter choice");
            ch = scanner.nextInt();

            switch (ch) {
                case 1:
                    viewAllUsers(userDao);
                    break;
                case 2:
                    viewAllUsersAndTrips(userDao, tripDao);
                    break;
                case 3:
                    System.out.println("Logged out Successfully!!!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (ch != 3);
    }

    private static void viewAllUsers(UserDao userDao) {
        List<User> userList = userDao.getAllUsers();
        System.out.println("Users:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    private static void viewAllUsersAndTrips(UserDao userDao, TripDao tripDao) {
        List<User> userList = userDao.getAllUsers();
        System.out.println("Users and their Trips:");
        for (User user : userList) {
            System.out.println(user);
            List<Trip> tripList = tripDao.getTripsByUserId(user.getUserId());
            for (Trip trip : tripList) {
                System.out.println("\t" + trip);
            }
        }
    }

    private static void userOperations(Scanner scanner, User currentUser, TripDao tripDao) {
        int ch;
        do {
            System.out.println("\n1. Add Trip \n2. View Trips \n3. Update Trip \n4. Delete Trip \n5. Logout");
            System.out.println("Enter choice");
            ch = scanner.nextInt();
            switch (ch) {
                case 1:
                    addTrip(scanner, currentUser, tripDao);
                    break;
                case 2:
                    viewTrips(scanner, currentUser, tripDao);
                    break;
                case 3:
                    updateTrip(scanner, currentUser, tripDao);
                    break;
                case 4:
                    deleteTrip(scanner, currentUser, tripDao);
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logged out Successfully!!!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (ch != 5);
    }

    private static void addTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        System.out.println("Enter destination, accommodation, transport, and schedule");
        String destination = scanner.next();
        String accommodation = scanner.next();
        String transport = scanner.next();
        String schedule = scanner.next();

        Trip trip = new Trip();
        trip.setUserId(currentUser.getUserId());
        trip.setDestination(destination);
        trip.setAccommodation(accommodation);
        trip.setTransport(transport);
        trip.setSchedule(schedule);
        tripDao.createTrip(trip);

        System.out.println("Trip added successfully.");
    }

    private static void viewTrips(Scanner scanner, User currentUser, TripDao tripDao) {
        List<Trip> tripList = tripDao.getTripsByUserId(currentUser.getUserId());
        System.out.println("Your Trips:");
        for (Trip trip : tripList) {
            System.out.println(trip);
        }
    }

    private static void updateTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        viewTrips(scanner, currentUser, tripDao);
        System.out.println("Enter the ID of the trip you want to update:");
        int tripId = scanner.nextInt();

        Trip tripToUpdate = tripDao.getTripById(tripId);
        if (tripToUpdate == null || tripToUpdate.getUserId() != currentUser.getUserId()) {
            System.out.println("Invalid Trip ID or this trip doesn't belong to you.");
            return;
        }

        System.out.println("Enter destination, accommodation, transport, and schedule");
        String destination = scanner.next();
        String accommodation = scanner.next();
        String transport = scanner.next();
        String schedule = scanner.next();

        tripToUpdate.setDestination(destination);
        tripToUpdate.setAccommodation(accommodation);
        tripToUpdate.setTransport(transport);
        tripToUpdate.setSchedule(schedule);
        tripDao.updateTrip(tripToUpdate);

        System.out.println("Trip updated successfully.");
    }

    private static void deleteTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        viewTrips(scanner, currentUser, tripDao);
        System.out.println("Enter the ID of the trip you want to delete:");
        int tripId = scanner.nextInt();

        Trip tripToDelete = tripDao.getTripById(tripId);
        if (tripToDelete == null || tripToDelete.getUserId() != currentUser.getUserId()) {
            System.out.println("Invalid Trip ID or this trip doesn't belong to you.");
            return;
        }

        tripDao.deleteTrip(tripId);
        System.out.println("Trip deleted successfully.");
    }
}



/*

package com.travelplanner.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.travelplanner.model.Trip;
import com.travelplanner.model.User;
import com.travelplanner.dao.TripDao;
import com.travelplanner.dao.TripDaoImpl;
import com.travelplanner.dao.UserDao;
import com.travelplanner.dao.UserDaoImpl;

public class TravelPlannerMain {
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDaoImpl();
        TripDao tripDao = new TripDaoImpl();
        List<Trip> tripList = new ArrayList<>();
        User currentUser = null;

        System.out.println("Travel Planner Application");

        // Login or Register
        while (currentUser == null) {
            System.out.println("1. Login \n2. Register");
            System.out.println("Enter choice");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser = login(scanner, userDao);
                    break;
                case 2:
                    currentUser = register(scanner, userDao);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        System.out.println("Welcome, " + currentUser.getFullName());
        
        

        // Continue with other operations
        int ch;
        do {
            System.out.println("\n1. Add Trip \n2. View Trips \n3. Update Trip \n4. Delete Trip \n5. Logout");
            System.out.println("Enter choice");
            ch = scanner.nextInt();
            switch (ch) {
                case 1:
                    addTrip(scanner, currentUser, tripDao);
                    break;
                case 2:
                    viewTrips(scanner, currentUser, tripDao);
                    break;
                case 3:
                    updateTrip(scanner, currentUser, tripDao);
                    break;
                case 4:
                    deleteTrip(scanner, currentUser, tripDao);
                    break;
                case 5:
                    currentUser = null; // Logout
                    System.out.println("User is Logout");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (ch != 5);

        scanner.close();
    }

    private static User login(Scanner scanner, UserDao userDao) {
        // Implementation of login
        System.out.println("Enter your email:");
        String email = scanner.next();

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            System.out.println("User not found. Please register first.");
            return null;
        }

        System.out.println("Enter your password:");
        String password = scanner.next();

        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return null;
        }

        return user;
    }

    private static User register(Scanner scanner, UserDao userDao) {
        // Implementation of registration
        User user = new User();
        System.out.println("Enter your full name:");
        user.setFullName(scanner.next());
        scanner.nextLine();

        System.out.println("Enter your email:");
        user.setEmail(scanner.next());

        System.out.println("Enter your phone number:");
        try {
            user.setPhone(scanner.nextLong());
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input for phone number. Please enter a valid phone number.");
            scanner.nextLine(); // Consume the remaining input line to prevent further issues.
            return null; // Exit the method since the phone number is not valid.
        }
        scanner.nextLine(); // Consume the newline character after reading the phone number.

        System.out.println("Enter your password:");
        user.setPassword(scanner.next());

        userDao.createUser(user);
        System.out.println("Registration successful. Please login.");
        return null;
    }

    private static void addTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        // Implementation of adding a trip
        System.out.println("Enter destination, accommodation, transport, and schedule");
        String destination = scanner.next();
        String accommodation = scanner.next();
        String transport = scanner.next();
        String schedule = scanner.next();

        Trip trip = new Trip();
        trip.setUserId(currentUser.getUserId());
        trip.setDestination(destination);
        trip.setAccommodation(accommodation);
        trip.setTransport(transport);
        trip.setSchedule(schedule);
        tripDao.createTrip(trip);

        System.out.println("Trip added successfully.");
    }

    private static void viewTrips(Scanner scanner, User currentUser, TripDao tripDao) {
        // Implementation of viewing trips
        List<Trip> tripList = tripDao.getTripsByUserId(currentUser.getUserId());
        for (Trip trip : tripList) {
            System.out.println(trip);
        }
    }

    private static void updateTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        // Implementation of updating a trip
        System.out.println("Enter trip ID to update trip");
        int tripId;
        try {
            tripId = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid trip ID (an integer).");
            scanner.nextLine(); // Consume the remaining input line to prevent further issues.
            return; // Exit the method since the trip ID is not valid.
        }

        Trip tripToUpdate = tripDao.getTripById(tripId);
        if (tripToUpdate != null) {
            System.out.println("Enter new destination, accommodation, transport, and schedule");
            String destination = scanner.next();
            String accommodation = scanner.next();
            String transport = scanner.next();
            String schedule = scanner.next();

            tripToUpdate.setDestination(destination);
            tripToUpdate.setAccommodation(accommodation);
            tripToUpdate.setTransport(transport);
            tripToUpdate.setSchedule(schedule);

            tripDao.updateTrip(tripToUpdate);
            System.out.println("Trip updated successfully.");
        } else {
            System.out.println("Trip not found.");
        }
    }

    private static void deleteTrip(Scanner scanner, User currentUser, TripDao tripDao) {
        // Implementation of deleting a trip
        System.out.println("Enter trip ID to delete trip");
        int tripId;
        try {
            tripId = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid trip ID (an integer).");
            scanner.nextLine(); // Consume the remaining input line to prevent further issues.
            return; // Exit the method since the trip ID is not valid.
        }

        tripDao.deleteTrip(tripId);
        System.out.println("Trip deleted successfully.");
    }
}

*/
