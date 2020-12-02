/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblarticle.TblArticleDAO;
import hieulm.tblcomment.TblCommentDAO;
import hieulm.tblemotion.TblEmotionDAO;
import hieulm.tblnotification.TblNotificationDAO;
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
@WebServlet(name = "DeleteArticleServlet", urlPatterns = {"/DeleteArticleServlet"})
public class DeleteArticleServlet extends HttpServlet {

    final String SEARCH_PAGE = "searchPage.jsp";

    static Logger log = Logger.getLogger(DeleteArticleServlet.class.getName());

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

	String postId = request.getParameter("postId");

	try {
	    TblArticleDAO articleDAO = new TblArticleDAO();
	    boolean result = articleDAO.deleteArticle(Integer.parseInt(postId));
	    if (result) {
		TblCommentDAO commentDAO = new TblCommentDAO();
		commentDAO.removeCommentByPostId(Integer.parseInt(postId));

		TblEmotionDAO emotionDAO = new TblEmotionDAO();
		emotionDAO.removeEmotionActiveByPostId(Integer.parseInt(postId));

		TblNotificationDAO notificationDAO = new TblNotificationDAO();
		notificationDAO.removeNotificationByPost(Integer.parseInt(postId));
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (NumberFormatException ex) {
	    log.error("NumberFormatException " + ex.getMessage());
	} finally {
	    response.sendRedirect(SEARCH_PAGE);
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
