/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tbluser.TblUserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "ActiveAccountServlet", urlPatterns = {"/ActiveAccountServlet"})
public class ActiveAccountServlet extends HttpServlet {
    
    final String LOGIN_PAGE = "login.html";
    final String ACTIVE_ACCOUNT_PAGE = "activeAccountPage.jsp";
    
    static Logger log = Logger.getLogger(ActiveAccountServlet.class.getName());

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
	
	String url = ACTIVE_ACCOUNT_PAGE;
	
	String code = request.getParameter("txtCode");
	String activeCode = request.getParameter("activeCode");
	
	String email = request.getParameter("txtEmail");
	
	try {
	    if(code.equals(activeCode)){
		TblUserDAO dao = new TblUserDAO();
		boolean result = dao.activeAccount(email);
		if(result){
		    url = LOGIN_PAGE;
		}
	    }
	    else{
		request.setAttribute("ACTIVE_ERROR", "The code you input is not match.");
		request.setAttribute("ACTIVATION_CODE", activeCode);
		request.setAttribute("USER_EMAIL", email);
	    }
	}
	catch(NamingException ex){
	    log.error("NamingException " + ex.getMessage());
	}
	catch(SQLException ex){
	    log.error("SQLException " + ex.getMessage());
	}
	finally{
	    if(url.equals(LOGIN_PAGE)){
		response.sendRedirect(url);
	    }
	    else{
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	    }
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
