/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblcomment;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblCommentDTO implements Serializable{
    private int commentId;
    private int postId;
    private String userEmail;
    private String commentContent;
    private String commentDate;

    public TblCommentDTO() {
    }

    public TblCommentDTO(int commentId, int postId, String userEmail, String commentContent, String commentDate) {
	this.commentId = commentId;
	this.postId = postId;
	this.userEmail = userEmail;
	this.commentContent = commentContent;
	this.commentDate = commentDate;
    }

    /**
     * @return the commentId
     */
    public int getCommentId() {
	return commentId;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(int commentId) {
	this.commentId = commentId;
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
     * @return the commentContent
     */
    public String getCommentContent() {
	return commentContent;
    }

    /**
     * @param commentContent the commentContent to set
     */
    public void setCommentContent(String commentContent) {
	this.commentContent = commentContent;
    }

    /**
     * @return the commentDate
     */
    public String getCommentDate() {
	return commentDate;
    }

    /**
     * @param commentDate the commentDate to set
     */
    public void setCommentDate(String commentDate) {
	this.commentDate = commentDate;
    }
}
