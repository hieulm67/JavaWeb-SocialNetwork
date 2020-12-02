/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblarticle.TblArticleDAO;
import hieulm.tblarticle.TblArticleDTO;
import hieulm.tblcomment.TblCommentDAO;
import hieulm.tblcomment.TblCommentDTO;
import hieulm.tblemotion.TblEmotionDAO;
import hieulm.tblemotion.TblEmotionDTO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ViewArticleServlet", urlPatterns = {"/ViewArticleServlet"})
public class ViewArticleServlet extends HttpServlet {
    
    final String VIEW_DETAIL_ARTICLE_PAGE = "viewArticle.jsp";

    final String SOURCE_IMAGE_LIKE = "./assets/img/like.png";
    final String SOURCE_IMAGE_DISLIKE = "./assets/img/dislike.jpg";
    final String SOURCE_IMAGE_CURRENT_LIKE = "./assets/img/currentLike.png";
    final String SOURCE_IMAGE_CURRENT_DISLIKE = "./assets/img/currentDislike.png";
    
    static Logger log = Logger.getLogger(ViewArticleServlet.class.getName());
    
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
	
	String url = VIEW_DETAIL_ARTICLE_PAGE;
	
	String postId = request.getParameter("postId");
	
	try{
	    if(postId.trim().length() > 0){
		TblArticleDAO articleDAO = new TblArticleDAO();
		TblArticleDTO articleDTO = articleDAO.searchArticle( Integer.parseInt(postId) );
		
		if(articleDTO != null){
		    request.setAttribute("POST_DETAIL", articleDTO);
		    
		    TblCommentDAO commentDAO = new TblCommentDAO();
		    commentDAO.searchCommentByPostId( Integer.parseInt(postId) );
		    List<TblCommentDTO> listComment = commentDAO.getListComment();
		    
		    if(listComment != null){
			request.setAttribute("LIST_COMMENTS", listComment);
		    }
		    
		    TblEmotionDAO emotionDAO = new TblEmotionDAO();
		    long totalLike = emotionDAO.calculateLikeEmotionByPost(Integer.parseInt(postId));
		    long totalDislike = emotionDAO.calculateDislikeEmotionByPost(Integer.parseInt(postId));
		    
		    if(totalLike > 0){
			request.setAttribute("TOTAL_LIKE", totalLike);
		    }
		    if(totalDislike > 0){
			request.setAttribute("TOTAL_DISLIKE", totalDislike);
		    }
		    
		    request.setAttribute("SOURCE_IMAGE_LIKE", SOURCE_IMAGE_LIKE);
		    request.setAttribute("SOURCE_IMAGE_DISLIKE", SOURCE_IMAGE_DISLIKE);
		    
		    HttpSession session = request.getSession(false);
		    if(session != null){
			TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			
			if(currentUser != null){
			    TblEmotionDTO emotionDTO = emotionDAO.getEmotionPostActive(Integer.parseInt(postId), currentUser.getEmail());
			    
			    if(emotionDTO != null){
				if(emotionDTO.getEmotionType().equals("Like")){
				    request.setAttribute("SOURCE_IMAGE_LIKE", SOURCE_IMAGE_CURRENT_LIKE);
				}
				else{
				    request.setAttribute("SOURCE_IMAGE_DISLIKE", SOURCE_IMAGE_CURRENT_DISLIKE);
				}
			    }
			}
		    }
		}
	    }
	}
	catch(IOException ex){
	    log.error("IOException " + ex.getMessage());
	}
	catch(NamingException ex){
	    log.error("NamingException " + ex.getMessage());
	}
	catch(SQLException ex){
	    log.error("SQLException " + ex.getMessage());
	}
	catch(NumberFormatException ex){
	    log.error("NumberFormatException " + ex.getMessage());
	}
	finally{
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
