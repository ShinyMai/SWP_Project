/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

//import controller.authentication.BasedAuthorizationController;
//import controller.authentication.BasedRequiredAuthenticationController;
import DAO.AttendanceDBContext;
import DAO.SessionDBContext;
import entity.Account;
import entity.Student;
import entity.Attendance;
import entity.Role;
import entity.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AttendanceController extends HttpServlet {// extends BasedAuthorizationController {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionDBContext sesDB = new SessionDBContext();
        Session s = new Session();
        int id = Integer.parseInt(request.getParameter("id"));
        s.setSession_id(id);
        Session ses = sesDB.get(s);
        request.setAttribute("ses", ses);

        AttendanceDBContext attDB = new AttendanceDBContext();
        ArrayList<Attendance> attendances = attDB.getAttendances(id);

        request.setAttribute("atts", attendances);
        request.getRequestDispatcher("...").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] stuids = request.getParameterValues("student_id");
        Session ses = new Session();
        ses.setSession_id(Integer.parseInt(request.getParameter("session_id")));
        ArrayList<Attendance> atts = new ArrayList<>();
        for (String stu_id : stuids) {
            String student_id = stu_id;
            Attendance a = new Attendance();
            Student s = new Student();
            s.setStudent_id(student_id);
            a.setStudent(s);
            a.setSession(ses);
            a.setAtt_description(request.getParameter("att_description" + stu_id));
            a.setStatus(request.getParameter("status" + stu_id).equals("Attended"));
            atts.add(a);
        }
        ses.setAtts(atts);
        SessionDBContext sesDB = new SessionDBContext();
        sesDB.addAttendances(ses);
        String id = request.getParameter("session_id");
        response.sendRedirect("attended?id=" + id);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
