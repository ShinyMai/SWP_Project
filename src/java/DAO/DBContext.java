package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author FPT University - PRJ301
 */
public class DBContext{
    protected Connection connection;
    public DBContext()
    {
        //@Students: You are allowed to edit user, pass, url variables to fit 
        //your system configuration
        //You can also add more methods for Database Interaction tasks. 
        //But we recommend you to do it in another class
        // For example : StudentDBContext extends DBContext , 
        //where StudentDBContext is located in dal package, 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/swp391_g4_ver1"; 
            String user = "admin";
            String password = "12345";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection OK!");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
