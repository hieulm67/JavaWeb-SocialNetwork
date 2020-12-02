/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblstatus.TblStatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    final String INVALID_PAGE = "invalid.html";
    final String SEARCH_PAGE = "searchPage.jsp";
    final String PERMISSION_DENY_PAGE = "permissionDeny.html";
    
    static Logger log = Logger.getLogger(LoginServlet.class.getName());

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

	String email = request.getParameter("txtEmail");
	String password = request.getParameter("txtPassword");
	
	String url = INVALID_PAGE;

	try {
	    TblUserDAO userDAO = new TblUserDAO();
	    String encryptedPassword = userDAO.encryptePassword(password);

	    TblUserDTO dto = userDAO.checkLogin(email, encryptedPassword);
	    if (dto != null) {
		if (TblStatusDAO.checkUserStatus(dto.getStatus()).equals("Active")) {
		    HttpSession session = request.getSession();
		    session.setAttribute("CURRENT_USER", dto);

		    Cookie usernameCookie = new Cookie("USERNAME", dto.getEmail());
		    usernameCookie.setMaxAge(60 * 3);
		    response.addCookie(usernameCookie);

		    Cookie passwordCookie = new Cookie("PASSWORD", encryptedPassword);
		    passwordCookie.setMaxAge(60 * 3);
		    response.addCookie(passwordCookie);

		    url = SEARCH_PAGE;
		}
		else{
		    url = PERMISSION_DENY_PAGE;
		}
	    }
	} catch (NoSuchAlgorithmException ex) {
	    log.error("NoSuchAlgorithmException " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
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
