/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblarticle.TblArticleDAO;
import hieulm.tblarticle.TblArticleDTO;
import hieulm.tblemotion.TblEmotionDAO;
import hieulm.tblemotion.TblEmotionDTO;
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
@WebServlet(name = "ReactEmotionArticleServlet", urlPatterns = {"/ReactEmotionArticleServlet"})
public class ReactEmotionArticleServlet extends HttpServlet {

    final String DEACTIVE_EMOTION_STATUS_ID = "D";
    final String ACTIVE_EMOTION_STATUS_ID = "A";
    final String EMOTION_NOTIFICATION_TYPE = "React";

    static Logger log = Logger.getLogger(ReactEmotionArticleServlet.class.getName());

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
	//current user
	String userEmail = request.getParameter("userEmail");

	String emotionType = request.getParameter("emotionType");

	boolean deactiveCurrentEmotion = false;

	try {
	    if (emotionType.trim().length() > 0) {
		TblEmotionDAO emotionDAO = new TblEmotionDAO();
		if (emotionDAO.checkEmotionActive(Integer.parseInt(postId), userEmail)) {

		    TblEmotionDTO dto = emotionDAO.getEmotionPostActive(Integer.parseInt(postId), userEmail);
		    boolean result = emotionDAO.updateEmotionStatus(Integer.parseInt(postId), userEmail, dto.getEmotionType(), DEACTIVE_EMOTION_STATUS_ID);

		    if (dto.getEmotionType().equals(emotionType) && result) {
			TblNotificationDAO notificationDAO = new TblNotificationDAO();
			TblNotificationDTO notificationDTO = notificationDAO.searchNotificationActiveByPostId(Integer.parseInt(postId), EMOTION_NOTIFICATION_TYPE);

			if (notificationDTO != null) {
			    if (notificationDTO.getUserEmail().equals(dto.getUserEmail())) {
				notificationDAO.removeNotificationByType(Integer.parseInt(postId), EMOTION_NOTIFICATION_TYPE);
			    }
			}

			deactiveCurrentEmotion = true;
		    }
		}

		if (!deactiveCurrentEmotion) {
		    TblArticleDAO articleDAO = new TblArticleDAO();
		    TblArticleDTO currentPost = articleDAO.searchArticle(Integer.parseInt(postId));

		    if (currentPost != null) {

			if (emotionDAO.checkEmotionByType(Integer.parseInt(postId), userEmail, emotionType)) {
			    boolean r = emotionDAO.updateEmotionStatus(Integer.parseInt(postId), userEmail, emotionType, ACTIVE_EMOTION_STATUS_ID);

			    if (!userEmail.equals(currentPost.getUserEmail())) {
				updateNotificationEmotion(userEmail, postId, emotionType);
			    }
			} else {
			    emotionDAO.insertEmotion(Integer.parseInt(postId), userEmail, emotionType);

			    if (!userEmail.equals(currentPost.getUserEmail())) {
				updateNotificationEmotion(userEmail, postId, emotionType);
			    }
			}
		    }
		}
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (NumberFormatException ex) {
	    log.error("NumberFormatException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} finally {
	    String url = "DispatchController?btAction=View Article"
		    + "&postId=" + postId;
	    response.sendRedirect(url);
	    out.close();
	}
    }

    private void updateNotificationEmotion(String userEmail, String postId, String emotionType) throws NamingException, SQLException {
	TblNotificationDAO notificationDAO = new TblNotificationDAO();
	String notificationContent = userEmail + " has reacted " + emotionType.toLowerCase() + " emotion";
	System.out.println("hello");
	if (!notificationDAO.searchNotificationByPostId(Integer.parseInt(postId), EMOTION_NOTIFICATION_TYPE)) {
	    System.out.println("alo");
	    notificationDAO.insertNotification(Integer.parseInt(postId), userEmail, notificationContent, EMOTION_NOTIFICATION_TYPE);
	} else {
	    notificationDAO.updateNotificationContent(Integer.parseInt(postId), userEmail, notificationContent, EMOTION_NOTIFICATION_TYPE);
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
