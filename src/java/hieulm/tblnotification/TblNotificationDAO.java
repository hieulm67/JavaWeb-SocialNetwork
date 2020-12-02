/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblnotification;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblNotificationDAO implements Serializable{
    
    final String ACTIVE_NOTIFICATION_STATUS_ID = "A";
    final String DEACTIVE_NOTIFICATION_STATUS_ID = "D";
    final String ACTIVE_NOTIFICATION_STATUS_NAME = "Active";
    
    public boolean insertNotification(int postId, String userEmail, String notificationContent, String notificationType) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblNotification(postId, userEmail, notificationContent, notificationType) "
			+ "VALUES(?, ?, ?, ?)");
		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		pst.setString(3, notificationContent);
		pst.setString(4, notificationType);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }
    
    private List<TblNotificationDTO> listNotification;

    public List<TblNotificationDTO> getListNotification() {
	return listNotification;
    }
    
    public void searchNotificationByUserEmail(String currentUserEmail) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT tblNotification.notificationId, tblNotification.postId, tblNotification.userEmail, tblNotification.notificationContent, tblNotification.notificationDate, tblNotification.notificationType "
			+ "FROM tblNotification JOIN tblArticle ON tblNotification.postId = tblArticle.postId "
			+ "WHERE tblArticle.userEmail = ? AND notificationStatus = ? "
			+ "ORDER BY tblNotification.notificationDate DESC");
		pst.setString(1, currentUserEmail);
		pst.setString(2, ACTIVE_NOTIFICATION_STATUS_ID);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if(listNotification == null){
			listNotification = new ArrayList<>();
		    }
		    
		    String notificationId = rs.getString("notificationId");
		    int postId = rs.getInt("postId");
		    String userEmail = rs.getString("userEmail");
		    String notificationContent = rs.getString("notificationContent");
		    Timestamp notificationDate = rs.getTimestamp("notificationDate");
		    String notificationType = rs.getString("notificationType");
		    
		    TblNotificationDTO dto = new TblNotificationDTO(notificationId, postId, userEmail, notificationContent, formatDate(notificationDate), notificationType);
		    listNotification.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }
    
    public TblNotificationDTO searchNotificationActiveByPostId(int postId, String notificationType) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT notificationId, postId, userEmail, notificationContent, notificationDate "
			+ "FROM tblNotification JOIN tblStatus ON tblNotification.notificationStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND notificationType = ? AND statusName = ?");
		pst.setInt(1, postId);
		pst.setString(2, notificationType);
		pst.setString(3, ACTIVE_NOTIFICATION_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    String notificationId = rs.getString("notificationId");
		    String userEmail = rs.getString("userEmail");
		    String notificationContent = rs.getString("notificationContent");
		    Timestamp notificationDate = rs.getTimestamp("notificationDate");
		    
		    TblNotificationDTO dto = new TblNotificationDTO(notificationId, postId, userEmail, notificationContent, notificationDate.toString(), notificationType);
		    return dto;
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return null;
    }
    
    public boolean searchNotificationByPostId(int postId, String notificationType) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT notificationId "
			+ "FROM tblNotification "
			+ "WHERE postId = ? AND notificationType = ?");
		pst.setInt(1, postId);
		pst.setString(2, notificationType);
		rs = pst.executeQuery();
		if (rs.next()) {
		    return true;
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }
    
    public boolean updateNotificationContent(int postId, String userEmail, String notificationContent, String notificationType) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblNotification "
			+ "SET notificationContent = ?, userEmail = ?, notificationDate = ?, notificationStatus = ? "
			+ "WHERE postId = ? AND notificationType = ?");
		pst.setString(1, notificationContent);
		pst.setString(2, userEmail);
		
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		pst.setTimestamp(3, timestamp);
		
		pst.setInt(5, postId);
		pst.setString(6, notificationType);
		pst.setString(4, ACTIVE_NOTIFICATION_STATUS_ID);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }
    
    public boolean removeNotificationByType(int postId, String notificationType) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblNotification "
			+ "SET notificationStatus = ? "
			+ "WHERE postId = ? AND notificationType = ?");
		
		pst.setString(1, DEACTIVE_NOTIFICATION_STATUS_ID);
		pst.setInt(2, postId);
		pst.setString(3, notificationType);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }
    
    public boolean removeNotificationByPost(int postId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblNotification "
			+ "SET notificationStatus = ? "
			+ "WHERE postId = ?");
		
		pst.setString(1, DEACTIVE_NOTIFICATION_STATUS_ID);
		pst.setInt(2, postId);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }
    
    private String formatDate(Timestamp notificationDate){
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	return formatter.format(notificationDate);
    }
}
