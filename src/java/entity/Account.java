/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author HP
 */
public class Account {
    public String userName, password, student_id, instructor_id;
    public int role_id, account_id;

    public Account() {
    }

    public Account(String userName, String password, int role_id, int account_id) {
        this.userName = userName;
        this.password = password;
        this.role_id = role_id;
        this.account_id = account_id;
    }

    public Account(String student_id, String instructor_id) {
        this.student_id = student_id;
        this.instructor_id = instructor_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(String instructor_id) {
        this.instructor_id = instructor_id;
    }

    @Override
    public String toString() {
        return "Account{" + "userName=" + userName + ", password=" + password + ", role_id=" + role_id + ", account_id=" + account_id + ", student_id=" + student_id + ", instructor_id=" + instructor_id + '}';
    }  
}
