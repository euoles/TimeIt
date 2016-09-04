/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author George
 */
public class DbConnection {

    //private String jdbcDriver ="com.mysql.jdbc.Driver";
    private String jdbcDriver = "org.sqlite.JDBC";
    //private String dbAddress = "jdbc:mysql://localhost/";
    private String dbAddress = "jdbc:sqlite:testDB.db";
    //private String dbName = "timeitdb";
    private String dbName = "testDB";
    private String userpassword = "?user=root&password=";
    private String username = "root";
    private String password = "";

    public ResultSet resultSet;
    public Connection connection;
    public PreparedStatement preparedStatement;
    public Statement statement;

    public DbConnection() {
        try {
            //new added code

            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress);//+dbName+userpassword);              
            //statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
           statement = connection.createStatement();
            
            

        } catch (Exception e) {
            System.out.println("Class.forName Exception/n-----------------");
            e.printStackTrace();
            
        }
    }
    
    public Connection Connector(){
        return connection;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

//    public void DbConnectionold() {
//        try {
//            //new added code
//            if (dbCheck()) {
//                JOptionPane.showMessageDialog(null, "Database Exist");
//
//                /*
//                 Class.forName(jdbcDriver);
//                 connection = DriverManager.getConnection(dbAddress);//+dbName+userpassword);              
//                 statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//                
//                 */
//            } else {
//                // statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//                // statement.executeUpdate("create database " + dbName);
//                JOptionPane.showMessageDialog(null, "Sorry !!! No such database found");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Class.forName Exception/n-----------------");
//            e.printStackTrace();
//        }
//    }

    

}
