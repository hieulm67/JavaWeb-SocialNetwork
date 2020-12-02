/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblarticle.TblArticleCreateNewError;
import hieulm.tblarticle.TblArticleDAO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"})
@MultipartConfig
public class PostArticleServlet extends HttpServlet {

    final String SEARCH_PAGE = "searchPage.jsp";
    final String POST_ARTICLE_ERROR_PAGE = "postArticle.jsp";

    static Logger log = Logger.getLogger(PostArticleServlet.class.getName());

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

	String url = POST_ARTICLE_ERROR_PAGE;

	String content = request.getParameter("txtContent");
	String description = request.getParameter("txtDescription");

	Part imagePart = request.getPart("imageSelect");

	TblArticleCreateNewError errors = new TblArticleCreateNewError();
	boolean errorFound = false;

	try {
	    if (content.trim().length() > 200 || content.trim().length() < 1) {
		errorFound = true;
		errors.setPostContentLengthError("Post content must contain 1-200 characters.");
	    }
	    if (description.trim().length() > 500 || description.trim().length() < 1) {
		errorFound = true;
		errors.setPostDescriptionLengthError("Post description must contain 1-500 characters.");
	    }

	    if (errorFound) {
		request.setAttribute("CREATE_ARTICLE_ERROR", errors);
	    } else {
		TblArticleDAO dao = new TblArticleDAO();

		HttpSession session = request.getSession(false);
		if (session != null) {
		    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");

		    boolean result = dao.insertArticle(imagePart, content, description, currentUser.getEmail());
		    if (result) {
			url = SEARCH_PAGE;
		    }
		}
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (IOException ex) {
	    log.error("IOException " + ex.getMessage());
	} finally {
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
