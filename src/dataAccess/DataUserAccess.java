/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import businessLogic.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.DatabaseFactory;

/**
 * This is the DataUserAccess class for accessing USERS table from database
 *
 * @author Timothy
 */
public class DataUserAccess {

    Connection dataPersistenceLayer;
    ArrayList<User> storedUsers = new ArrayList<>();

    public DataUserAccess() {
        // Use DatabaseFactory to get a handle for database connection
        this.dataPersistenceLayer = DatabaseFactory.getConnectionInstance();
        this.storedUsers = storedUsers;
        this.loadDataFromDB();
    }

    private void loadDataFromDB() {
        try {
            User user = null;
            Statement sql = this.dataPersistenceLayer.createStatement();
            ResultSet records = sql.executeQuery("SELECT * from USERS");

            while (records.next()) {
                user = new User();
                user.setFirstName(records.getString("FIRSTNAME"));
                user.setLastName(records.getString("LASTNAME"));
                user.setEmail(records.getString("EMAIL"));
                user.setPassword(records.getString("PASSWORD"));
                user.setAddress(records.getString("ADDRESS"));

                this.storedUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error loading user databse " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<User> retrieve() {
        // Code to be added to make sure storedUsers updated when a new user
        // is created
        this.storedUsers = new ArrayList<>();
        this.loadDataFromDB();
        //
        return this.storedUsers;
    }

    public boolean create(User input) {
        // // before adding User input into list, you have to check if it exists or not.
        if (!isUserExistInList(input)) {
            this.storedUsers.add(input);
            String createUserSql = "INSERT INTO USERS values ('" + input.getEmail() + "', '"
                    + escapeSingleQuoteSql(input.getFirstName()) + "', '"
                    + escapeSingleQuoteSql(input.getLastName()) + "', '"
                    + escapeSingleQuoteSql(input.getPassword()) + "', '"
                    + escapeSingleQuoteSql(input.getAddress()) + "')";
            return saveToDB(createUserSql);
        }
        return false;
    }

    public void update(User input) {
        for (int i = 0; i < this.storedUsers.size(); i++) {
            if (this.storedUsers.get(i).getEmail().equals(input.getEmail())) {
                String updateSql = "Update USERS Set"
                        + " FIRSTNAME='" + escapeSingleQuoteSql(input.getFirstName())
                        + "', LASTNAME='" + escapeSingleQuoteSql(input.getLastName())
                        + "', ADDRESS='" + escapeSingleQuoteSql(input.getAddress())
                        + "', PASSWORD='" + escapeSingleQuoteSql(input.getPassword())
                        + "' WHERE EMAIL='" + input.getEmail() + "'";
                this.storedUsers.set(i, input);
                saveToDB(updateSql);
                break;
            }
        }
    }

    public void delete(String email) {
        for (int i = 0; i < this.storedUsers.size(); i++) {
            if (this.storedUsers.get(i).getEmail().equals(email)) {
                User user = this.storedUsers.get(i);
                String deleteSql = "Delete from USERS where EMAIL='" + user.getEmail() + "'";
                saveToDB(deleteSql);
                this.storedUsers.remove(i);
                break;
            }
        }
    }

    private boolean isUserExistInList(User input) {
        for (int i = 0; i < storedUsers.size(); i++) {
            if (storedUsers.get(i).getEmail().equals(input.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private boolean saveToDB(String sqlQuery) {
        boolean isSaveToDBSuccessful = false;
        try {
            Statement sql = this.dataPersistenceLayer.createStatement();
            System.out.println(sqlQuery);
            sql.executeUpdate(sqlQuery);
            isSaveToDBSuccessful = true;
        } catch (SQLException ex) {
            isSaveToDBSuccessful = false;
            System.out.println("Error saving user info into database");
            ex.printStackTrace();
        }
        return isSaveToDBSuccessful;
    }
    
    /**
     * 
     * @param string a string
     * @return if this string has single quote, then replace single quote to
     * double quote
     */
    private String escapeSingleQuoteSql(String string) {
        return string.replaceAll("'", "''");
    }
}
