/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.DBContext;
import entity.Attendance;
import entity.Group;
import entity.Session;
import entity.Student;
import entity.Subject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adamin
 */
public class AttendanceDBContext extends DBContext{

    public ArrayList<Attendance> getAttendances(int session_id) {
        ArrayList<Attendance> atts = new ArrayList<>();
        try {
            String sql = " SELECT \n"
                    + "    s.student_id, \n"
                    + "    s.student_name, \n"
                    + "    IFNULL(a.status, 0) AS status, \n"
                    + "    IFNULL(a.att_description, 'nothing') AS att_description, \n"
                    + "    IFNULL(a.att_datetime, NOW()) AS att_datetime, \n"
                    + "    a.session_id\n"
                    + "FROM Session ses \n"
                    + "INNER JOIN Class_subject_mapping csm ON csm.class_id = s.class_id\n"
                    + "INNER JOIN Attendance a ON a.session_id = ses.session_id\n"
                    + "INNER JOIN Student stu ON stu.student_id = a.student_id\n"
                    + "INNER JOIN Class c ON c.class_id = csm.class_id\n"
                    + "WHERE ses.session_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, session_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();
                Student student = new Student();
                Session session = new Session();
                student.setStudent_id(rs.getString("student_id"));
                student.setStudent_name(rs.getString("student_name"));
                student.setUsername(rs.getString("username"));
                session.getStudent();
                session.setSession_id(rs.getInt("session_id"));
                att.setStudent(student);
                att.setSession(session);
                att.setStatus(rs.getBoolean("status"));
                att.setAtt_description(rs.getString("att_description"));
                att.setAtt_datetime(rs.getTimestamp("att_datetime"));
                atts.add(att);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atts;
    }

    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insert(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void update(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Attendance get(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
