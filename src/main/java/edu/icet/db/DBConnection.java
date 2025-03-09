package edu.icet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection CustomerList;
    private DBConnection() throws SQLException {
        this.CustomerList = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1234" );
    }
    public Connection getConnection(){
        return CustomerList;
    }
    public static DBConnection getInstance() throws SQLException {
        if (instance==null){
            return instance = new DBConnection();
        }
        return instance;
    }
}
