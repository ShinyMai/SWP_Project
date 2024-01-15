/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.ClassSubjectMapping;
import entity.Group;
import entity.Subject;
import entity.Session;
import entity.Instructor;
import entity.Student;
import entity.Attendance;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SessionDBContext extends DBContext {
    public ArrayList<Session> getSessionsByInstrucor (String instructor_id, Date from, Date to) {
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT i.instructor_id, i.instructor_name, su.subject_name, c.class_name, c.link_url,"
                    + " s.session_id, s.session_index, s.ses_date, s.isAtt \n"
                    + " FROM Session s INNER JOIN Class_subject_mapping csm ON csm.class_id = s.class_id\n"
                    + "				INNER JOIN Instructor i ON i.instructor_id = csm.instructor_id\n"
                    + "				INNER JOIN Class c ON c.class_id = csm.class_id\n"
                    + "				INNER JOIN Subject su ON su.subject_id = csm.subject_id\n"
                    + "		WHERE i.instructor_id = ? AND s.ses_date >= ? AND s.ses_date <= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, instructor_id);
            stm.setDate(2, from);
            stm.setDate(3, to);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Session session = new Session();
                session.setSession_id(rs.getInt("session_id"));
                session.setSession_date(rs.getDate("ses_date"));
                session.setIsAtt(rs.getBoolean("isAtt"));               
                Instructor instructor = new Instructor();
                instructor.setInstructor_id(rs.getString("instructor_id"));
                instructor.setInstructor_name(rs.getString("instructor_name"));
                session.getInstructor();
                ClassSubjectMapping csm = new ClassSubjectMapping();
                csm.setCsm_id(rs.getInt("csm_id"));
                session.getCsm();
                Group group = new Group();
                group.setGroup_id(rs.getString("class_id"));
                group.setGroup_name(rs.getString("class_name"));
                group.setLink_url(rs.getString("link_url"));
                session.getGroup();
                Subject subject = new Subject();
                subject.setSubject_id(rs.getString("subject_id"));
                subject.setSubject_name(rs.getString("subject_name"));
                session.getSubject();
                sessions.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessions;
    }
    
    public ArrayList<Session> getSessionsByStudent (String student_id, Date from, Date to) {
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT stu.student_id, stu.student_name, su.subject_name, c.class_name, c.link_url,"
                    + " s.session_id, s.session_index, s.ses_date, s.isAtt \n"
                    + "FROM Session s INNER JOIN Class_subject_mapping csm ON csm.class_id = s.class_id\n"
                    + "				INNER JOIN Attendance a ON a.session_id = s.session_id\n"
                    + "				INNER JOIN Student stu ON stu.student_id = a.student_id\n"
                    + "				INNER JOIN Class c ON c.class_id = csm.class_id\n"
                    + "				INNER JOIN Subject su ON su.subject_id = csm.subject_id\n"
                    + "		WHERE stu.student_id = ? AND s.ses_date >= ? AND s.ses_date <= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, student_id);
            stm.setDate(2, from);
            stm.setDate(3, to);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Session session = new Session();
                session.setSession_id(rs.getInt("session_id"));
                session.setSession_date(rs.getDate("ses_date"));
                session.setIsAtt(rs.getBoolean("isAtt"));
                Student student = new Student();
                student.setStudent_id(rs.getString("student_id"));
                student.setStudent_name(rs.getString("student_name"));
                session.getStudent();
                ClassSubjectMapping csm = new ClassSubjectMapping();
                csm.setCsm_id(rs.getInt("csm_id"));
                session.getCsm();
                Group group = new Group();
                group.setGroup_id(rs.getString("class_id"));
                group.setGroup_name(rs.getString("class_name"));
                group.setLink_url(rs.getString("link_url"));
                session.getGroup();
                Subject subject = new Subject();
                subject.setSubject_id(rs.getString("subject_id"));
                subject.setSubject_name(rs.getString("subject_name"));
                session.getSubject();
                Attendance att = new Attendance();
                att.setAtt_id(rs.getInt("att_id"));
                att.setStatus(rs.getBoolean("status"));
                session.getAttendance();
                sessions.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessions;
    }
    
    public void addAttendances(Session ses) {
        try {
            connection.setAutoCommit(false);
            String sql_update_isAtt = "UPDATE Session SET isAtt = 1 WHERE session_id =?";
            PreparedStatement stm_update_isAtt = connection.prepareStatement(sql_update_isAtt);
            stm_update_isAtt.setInt(1, ses.getSession_id());
            stm_update_isAtt.executeUpdate();

            String sql_remove_atts = "DELETE Attendance WHERE session_id =?";
            PreparedStatement stm_remove_atts = connection.prepareStatement(sql_remove_atts);
            stm_remove_atts.setInt(1, ses.getSession_id());
            stm_remove_atts.executeUpdate();
            
            String insertAttendanceQuery = "INSERT INTO Attendance (session_id, student_id, status, att_description, att_datetime) "
                    + "VALUES (?, ?, ?, ?,NOW())";
            PreparedStatement insertAttendanceStmt = connection.prepareStatement(insertAttendanceQuery);
            for (Attendance att : ses.getAtts()) {
                insertAttendanceStmt.setInt(1, ses.getSession_id());
                insertAttendanceStmt.setString(2, att.getStudent().getStudent_id());
                insertAttendanceStmt.setBoolean(3, att.isStatus());
                insertAttendanceStmt.setString(4, att.getAtt_description());
                insertAttendanceStmt.executeUpdate();
            }
            connection.commit();         
        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Session> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insert(Session entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void update(Session entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(Session entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Session get(Session entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
