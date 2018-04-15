/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseFactory class is created to make sure that there is only one database
 *
 * @author Timothy
 */
public final class DatabaseFactory {

    private DatabaseFactory() {
        // prevent instance initilisation of this util class
    }
    
    private static Connection connection = null;

    public static Connection getConnectionInstance() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IS2209");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get an instance of database connection");
        }
        return connection;
    }
}
