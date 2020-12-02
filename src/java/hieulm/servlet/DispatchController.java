/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MinHiu
 */
@MultipartConfig
public class DispatchController extends HttpServlet {
    
    final String LOGIN_PAGE = "login.html";
    final String LOGIN_CONTROLLER = "LoginServlet";
    final String REGISTER_NEW_ACCOUNT_CONTROLLER = "RegisterAccountServlet";
    final String LOGOUT_CONTROLLER = "LogoutServlet";
    final String STARTUP_CONTROLLER = "StartupServlet";
    final String SEARCH_CONTROLLER = "SearchServlet";
    final String POST_ARTICLE_CONTROLLER = "PostArticleServlet";
    final String UPLOAD_ARTICLE_CONTROLLER = "UploadArticleServlet";
    final String VIEW_ARTICLE_CONTROLLER = "ViewArticleServlet";
    final String COMMENT_ARTICLE_CONTROLLER = "CommentArticleServlet";
    final String DELETE_COMMENT_CONTROLLER = "DeleteCommentServlet";
    final String DELETE_ARTICLE_CONTROLLER = "DeleteArticleServlet";
    final String REACT_EMOTION_ARTICLE_CONTROLLER = "ReactEmotionArticleServlet";
    final String VIEW_NOTIFICATION_CONTROLLER = "ViewNotificationServlet";
    final String SEND_ACTIVATION_CODE_CONTROLLER = "SendActivationCodeServlet";
    final String ACTIVE_ACCOUNT_CONTROLLER = "ActiveAccountServlet";

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
	
	String button = request.getParameter("btAction");
	String url = LOGIN_PAGE;
	
	try{
	    if(button == null){
		url = STARTUP_CONTROLLER;
	    }
	    else if(button.equals("Login")){
		url = LOGIN_CONTROLLER;
	    }
	    else if(button.equals("Create New Account")){
		url = REGISTER_NEW_ACCOUNT_CONTROLLER;
	    }
	    else if(button.equals("Logout")){
		url = LOGOUT_CONTROLLER;
	    }
	    else if(button.equals("Search Article")){
		url = SEARCH_CONTROLLER;
	    }
	    else if(button.equals("Post Article")){
		url = POST_ARTICLE_CONTROLLER;
	    }
	    else if(button.equals("View Article")){
		url = VIEW_ARTICLE_CONTROLLER;
	    }
	    else if(button.equals("Comment Article")){
		url = COMMENT_ARTICLE_CONTROLLER;
	    }
	    else if(button.equals("Delete Comment")){
		url = DELETE_COMMENT_CONTROLLER;
	    }
	    else if(button.equals("Delete Article")){
		url = DELETE_ARTICLE_CONTROLLER;
	    }
	    else if(button.equals("React Emotion")){
		url = REACT_EMOTION_ARTICLE_CONTROLLER;
	    }
	    else if(button.equals("View Notification")){
		url = VIEW_NOTIFICATION_CONTROLLER;
	    }
	    else if(button.equals("Send Code")){
		url = SEND_ACTIVATION_CODE_CONTROLLER;
	    }
	    else if(button.equals("Active Account")){
		url = ACTIVE_ACCOUNT_CONTROLLER;
	    }
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
