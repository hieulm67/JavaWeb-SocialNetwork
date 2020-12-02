/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblnotification;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblNotificationDTO implements Serializable{
    private String notificationId;
    private int postId;
    private String userEmail;
    private String notificationContent;
    private String notificationDate;
    private String notificationType;

    public TblNotificationDTO() {
    }

    public TblNotificationDTO(String notificationId, int postId, String userEmail, String notificationContent, String notificationDate, String notificationType) {
	this.notificationId = notificationId;
	this.postId = postId;
	this.userEmail = userEmail;
	this.notificationContent = notificationContent;
	this.notificationDate = notificationDate;
	this.notificationType = notificationType;
    }

    /**
     * @return the notificationId
     */
    public String getNotificationId() {
	return notificationId;
    }

    /**
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(String notificationId) {
	this.notificationId = notificationId;
    }

    /**
     * @return the postId
     */
    public int getPostId() {
	return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(int postId) {
	this.postId = postId;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
	return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
    }

    /**
     * @return the notificationDate
     */
    public String getNotificationDate() {
	return notificationDate;
    }

    /**
     * @param notificationDate the notificationDate to set
     */
    public void setNotificationDate(String notificationDate) {
	this.notificationDate = notificationDate;
    }

    /**
     * @return the notificationType
     */
    public String getNotificationType() {
	return notificationType;
    }

    /**
     * @param notificationType the notificationType to set
     */
    public void setNotificationType(String notificationType) {
	this.notificationType = notificationType;
    }

    /**
     * @return the notificationContent
     */
    public String getNotificationContent() {
	return notificationContent;
    }

    /**
     * @param notificationContent the notificationContent to set
     */
    public void setNotificationContent(String notificationContent) {
	this.notificationContent = notificationContent;
    }
}
