package com.travelplanner.dao;

import com.travelplanner.model.User;
import com.travelplanner.util.DBHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {

    @Override
    public void createAdmin(User admin) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "INSERT INTO user (fullname, email, phone, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, admin.getFullName());
            ps.setString(2, admin.getEmail());
            ps.setLong(3, admin.getPhone());
            ps.setString(4, admin.getPassword());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    admin.setUserId(generatedId);
                    System.out.println("Admin with ID " + generatedId + " created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating the admin: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public void createUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "INSERT INTO user (fullname, email, phone, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setLong(3, user.getPhone());
            ps.setString(4, user.getPassword());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setUserId(generatedId);
                    System.out.println("User with ID " + generatedId + " created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public User getAdminByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User admin = null;
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM user WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                admin = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the admin: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return admin;
    }

    @Override
    public User getUserByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM user WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM user";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving users: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return userList;
    }

    @Override
    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "UPDATE user SET fullname = ?, phone = ?, password = ? WHERE userid = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setLong(2, user.getPhone());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User with ID " + user.getUserId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public void deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "DELETE FROM user WHERE userid = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User with ID " + userId + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userid"));
        user.setFullName(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getLong("phone"));
        user.setPassword(rs.getString("password"));
        return user;
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




/*
package com.travelplanner.dao;

import com.travelplanner.model.User;
import com.travelplanner.util.DBHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class UserDaoImpl implements UserDao {
    private List<User> userList;

    public UserDaoImpl() {
        this.userList = new ArrayList<>();
    }
    
    public void createUser(User user) {
        Connection conn = null;
        
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "INSERT INTO user (fullname, email, phone, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setLong(3, user.getPhone());
            ps.setString(4, user.getPassword());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setUserId(generatedId);
                    System.out.println("User with ID " + generatedId + " created successfully.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }


    public User getUserByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM user WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return user;
    }

    public List<User> getAllUsers() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            conn = DBHandler.getConnection();
            String query = "SELECT * FROM user";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving users: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return userList;
    }

    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "UPDATE user SET fullname = ?, phone = ?, password = ? WHERE userid = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setLong(2, user.getPhone());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User with ID " + user.getUserId() + " updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    public void deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBHandler.getConnection();
            String query = "DELETE FROM user WHERE userid = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User with ID " + userId + " deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the user: " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userid"));
        user.setFullName(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getLong("phone"));
        user.setPassword(rs.getString("password"));
        return user;
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


/*
*/