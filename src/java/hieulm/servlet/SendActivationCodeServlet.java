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
import java.util.Properties;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "SendActivationCodeServlet", urlPatterns = {"/SendActivationCodeServlet"})
public class SendActivationCodeServlet extends HttpServlet {

    final String ACTIVE_ACCOUNT_PAGE = "activeAccountPage.jsp";
    final String CONFIRM_MAIL_PAGE_ERROR = "confirmMailPage.jsp";

    final String HOST_NAME = "smtp.gmail.com";
    final int TSL_PORT = 587;
    final String APP_EMAIL = "lmhieu0607@gmail.com";
    final String APP_PASSWORD = "dcmspbbbpqfzvqud";

    static Logger log = Logger.getLogger(SendActivationCodeServlet.class.getName());

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
	
	String url = CONFIRM_MAIL_PAGE_ERROR;

	String receiveEmail = request.getParameter("txtEmail");

	try {
	    TblUserDAO dao = new TblUserDAO();
	    boolean result = dao.checkEmail(receiveEmail);
	    if (result) {
		String code = generateCode();
		
		sendEmail(code, receiveEmail);

		request.setAttribute("ACTIVATION_CODE", code);
		request.setAttribute("USER_EMAIL", receiveEmail);
		
		url = ACTIVE_ACCOUNT_PAGE;

		System.out.println("Message sent successfully");
	    }
	    else{
		request.setAttribute("CONFIRM_MAIL_ERROR", "The email had not registered.");
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (MessagingException ex) {
	    log.error("MessagingException " + ex.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
    }

    private void sendEmail(String code, String receiveEmail) throws MessagingException {
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.host", HOST_NAME);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port", TSL_PORT);

	Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
	    }
	});

	MimeMessage message = new MimeMessage(session);
	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
	message.setSubject("Activation Code");

	String html = "Your activation code is: <font style=\"font-weight: bold\">" + code + "</font><br>"
		+ "<br>Thank you for joining with us.";
	message.setContent(html, "text/html");

	// send message
	Transport.send(message);
    }

    private String generateCode() {
	String randomString = "";
	for (int i = 0; i < 6; i++) {
	    int ran = new Random().nextInt(10);
	    randomString += ran;
	}
	return randomString;
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
