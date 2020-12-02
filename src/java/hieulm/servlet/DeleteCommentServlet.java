/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblcomment.TblCommentDAO;
import hieulm.tblcomment.TblCommentDTO;
import hieulm.tblnotification.TblNotificationDAO;
import hieulm.tblnotification.TblNotificationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "DeleteCommentServlet", urlPatterns = {"/DeleteCommentServlet"})
public class DeleteCommentServlet extends HttpServlet {

    final String COMMENT_NOTIFICATION_TYPE = "Comment";

    static Logger log = Logger.getLogger(DeleteCommentServlet.class.getName());

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

	String commentId = request.getParameter("commentId");
	String postId = request.getParameter("postId");

	try {
	    TblCommentDAO commentDAO = new TblCommentDAO();
	    TblCommentDTO commentDTO = commentDAO.searchCommentByCommentId(Integer.parseInt(commentId));

	    if (commentDTO != null) {
		commentDAO.removeCommentByCommentId(Integer.parseInt(commentId));

		TblNotificationDAO notificationDAO = new TblNotificationDAO();
		TblNotificationDTO notificationDTO = notificationDAO.searchNotificationActiveByPostId(commentDTO.getPostId(), COMMENT_NOTIFICATION_TYPE);
		if (notificationDTO != null) {
		    if (notificationDTO.getUserEmail().equals(commentDTO.getUserEmail())) {
			notificationDAO.removeNotificationByType(commentDTO.getPostId(), COMMENT_NOTIFICATION_TYPE);
		    }
		}
	    }

	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (NumberFormatException ex) {
	    log.error("NumberFormatException " + ex.getMessage());
	} finally {
	    String url = "DispatchController?btAction=View Article"
		    + "&postId=" + postId;
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
