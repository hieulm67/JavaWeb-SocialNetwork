/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import hieulm.tbluser.TblUserCreateNewError;
import hieulm.tbluser.TblUserDAO;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/RegisterAccountServlet"})
public class RegisterAccountServlet extends HttpServlet {

    final String CREATE_ERROR_PAGE = "registerPage.jsp";
    final String CONFIRM_MAIL_PAGE = "confirmMailPage.html";
    
    static Logger log = Logger.getLogger(RegisterAccountServlet.class.getName());

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
	
	String url = CREATE_ERROR_PAGE;

	String email = request.getParameter("txtEmail");
	String name = request.getParameter("txtName");
	String password = request.getParameter("txtPassword");
	String confirmPassword = request.getParameter("txtConfirmPassword");

	boolean errorFound = false;
	TblUserCreateNewError errors = new TblUserCreateNewError();

	try {
	    if (!email.trim().matches("((\\w*)(\\d*))+@((\\w+)\\.(\\w+)){1}(\\.(\\w+))*") || email.length() > 100 || email.length() < 10) {
		errorFound = true;
		errors.setEmailFormatError("Email entered is invalid. Email must contain 10-100 characters and match pattern.");
	    }
	    if (password.trim().length() < 6 || password.trim().length() > 30) {
		errorFound = true;
		errors.setPasswordLengthError("Password must be contained 6-30 charcters.");
	    } else if (!confirmPassword.trim().equals(password.trim())) {
		errorFound = true;
		errors.setConfirmNoMatchedError("Confirm must be matched with password.");
	    }
	    if(name.trim().length() < 2 || name.trim().length() > 50){
               errorFound = true;
               errors.setNameLengthError("Name must be contained 2-50 charcters.");
	    }
	    if(errorFound){
		request.setAttribute("ERROR_CREATE_ACCOUNT", errors);
	    }
	    else{
		TblUserDAO dao = new TblUserDAO();
		String encodePassword = dao.encryptePassword(password);
		boolean result = dao.insertAccount(email, name, encodePassword);
		if(result){
		    url = CONFIRM_MAIL_PAGE;
		}
	    }

	}
	catch(NamingException ex){
	    log.error("NamingException " + ex.getMessage());
	}
	catch(SQLException ex){
	    log.error("SQLException " + ex.getMessage());
	    String msg = ex.getMessage();
	    if(msg.contains("duplicate")){
		errors.setEmailIsExisted(email + " is existed. Please try another.");
		request.setAttribute("ERROR_CREATE_ACCOUNT", errors);
	    }
	}
	catch(NoSuchAlgorithmException ex){
	    log.error("NoSuchAlgorithmException " + ex.getMessage());
	}
	finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
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
