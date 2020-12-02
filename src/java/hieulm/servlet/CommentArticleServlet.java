/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblarticle.TblArticleDAO;
import hieulm.tblarticle.TblArticleDTO;
import hieulm.tblcomment.TblCommentDAO;
import hieulm.tblnotification.TblNotificationDAO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "CommentArticleServlet", urlPatterns = {"/CommentArticleServlet"})
public class CommentArticleServlet extends HttpServlet {

    final String COMMENT_NOTIFICATION_TYPE = "Comment";

    static Logger log = Logger.getLogger(CommentArticleServlet.class.getName());

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

	String content = request.getParameter("txtComment");
	String postId = request.getParameter("postId");

	try {
	    if (content.trim().length() > 0) {
		HttpSession session = request.getSession(false);
		if (session != null) {

		    TblArticleDAO articleDAO = new TblArticleDAO();
		    TblArticleDTO articleDTO = articleDAO.searchArticle(Integer.parseInt(postId));
		    if (articleDTO != null) {
			TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			
			if (currentUser != null) {
			    TblCommentDAO commentDAO = new TblCommentDAO();
			    commentDAO.insertComment(Integer.parseInt(postId), currentUser.getEmail(), content);

			    if (!articleDTO.getUserEmail().equals(currentUser.getEmail())) {
				updateNotificationComment(currentUser.getEmail(), postId, content);
			    }
			}
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

    private void updateNotificationComment(String userEmail, String postId, String content) throws NamingException, SQLException {
	TblNotificationDAO notificationDAO = new TblNotificationDAO();
	String notificationContent = userEmail + " has commented: " + "\"" + content + "\"";
	if (!notificationDAO.searchNotificationByPostId(Integer.parseInt(postId), COMMENT_NOTIFICATION_TYPE)) {
	    notificationDAO.insertNotification(Integer.parseInt(postId), userEmail, notificationContent, COMMENT_NOTIFICATION_TYPE);
	} else {
	    notificationDAO.updateNotificationContent(Integer.parseInt(postId), userEmail, notificationContent, COMMENT_NOTIFICATION_TYPE);
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
