/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import hieulm.tbluser.TblUserDAO;
import hieulm.tbluser.TblUserDTO;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {
    final String LOGIN_PAGE = "login.html";
    final String SEARCH_PAGE = "searchPage.jsp";
    
    static Logger log = Logger.getLogger(StartupServlet.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");

	PrintWriter out = response.getWriter();
	
	String url = LOGIN_PAGE;

	try {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
		    String username = null;
		    String password = null;
		    for (Cookie cookie : cookies) {
			if (cookie.getName().equals("USERNAME")) {
			    username = cookie.getValue();
			} else if (cookie.getName().equals("PASSWORD")) {
			    password = cookie.getValue();
			}
		    }

		    if (username != null && password != null) {
			TblUserDAO dao = new TblUserDAO();
			TblUserDTO dto = dao.checkLogin(username, password);

			if (dto != null) {
			    session = request.getSession();
			    session.setAttribute("CURRENT_USER", dto);

			    url = SEARCH_PAGE;
			}
		    }
		}
	    }
	    else{
		url = SEARCH_PAGE;
	    }
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} finally {
	    response.sendRedirect(url);
	    out.close();
	}
    }

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
	processRequest(request, response);
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
	processRequest(request, response);
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
