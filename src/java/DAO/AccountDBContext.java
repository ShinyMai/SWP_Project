/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class AccountDBContext extends DBContext {
    
    public Account ValidateAccount(String userName, String passWord) {
        String sql = "Select * from Account where username = ? and password = ?";
        Account acc = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);
            st.setString(2, passWord);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {                
                acc = new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role_id"),
                        rs.getInt("account_id")
                        );
                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return acc;
    }
    
    public Account getAccountIdByUsername(String username) {
        String sql = "SELECT s.student_id, i.instructor_id FROM Account acc "
                + "LEFT JOIN Student s ON s.username = acc.username "
                + "LEFT JOIN Instructor i ON i.username = acc.username "
                + "WHERE acc.username=?";
        Account acc = null;
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();  
            while(rs.next()){
                acc = new Account(
                        rs.getString("student_id"),
                        rs.getString("instructor_id")
                );
                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e); // Handle exceptions appropriately in a real application
        }
        return acc;    
    }
    
    public static void main(String[] args) {
        AccountDBContext da = new AccountDBContext();
        System.out.println(da.getAccountIdByUsername("a3"));
    }
}
