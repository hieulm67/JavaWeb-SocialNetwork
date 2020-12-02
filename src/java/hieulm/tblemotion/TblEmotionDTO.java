/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblemotion;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblEmotionDTO implements Serializable{
    private int emotionId;
    private int postId;
    private String userEmail;
    private String emotionDate;
    private String emotionType;

    public TblEmotionDTO() {
    }

    public TblEmotionDTO(int emotionId, int postId, String userEmail, String emotionDate, String emotionType) {
	this.emotionId = emotionId;
	this.postId = postId;
	this.userEmail = userEmail;
	this.emotionDate = emotionDate;
	this.emotionType = emotionType;
    }

    /**
     * @return the emotionId
     */
    public int getEmotionId() {
	return emotionId;
    }

    /**
     * @param emotionId the emotionId to set
     */
    public void setEmotionId(int emotionId) {
	this.emotionId = emotionId;
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
     * @return the emotionDate
     */
    public String getEmotionDate() {
	return emotionDate;
    }

    /**
     * @param emotionDate the emotionDate to set
     */
    public void setEmotionDate(String emotionDate) {
	this.emotionDate = emotionDate;
    }

    /**
     * @return the emotionType
     */
    public String getEmotionType() {
	return emotionType;
    }

    /**
     * @param emotionType the emotionType to set
     */
    public void setEmotionType(String emotionType) {
	this.emotionType = emotionType;
    }
}
