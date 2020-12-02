/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblarticle;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblArticleDTO implements Serializable {

    private int postId;
    private String image;
    private String datePost;
    private String postTitle;
    private String postDescription;
    private String userEmail;

    public TblArticleDTO() {
    }

    public TblArticleDTO(int postId, String image, String datePost, String postTitle, String postDescription, String userEmail) {
	this.postId = postId;
	this.image = image;
	this.datePost = datePost;
	this.postTitle = postTitle;
	this.postDescription = postDescription;
	this.userEmail = userEmail;
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
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
	this.image = image;
    }

    /**
     * @return the datePost
     */
    public String getDatePost() {
	return datePost;
    }

    /**
     * @param datePost the datePost to set
     */
    public void setDatePost(String datePost) {
	this.datePost = datePost;
    }

    /**
     * @return the postContent
     */
    public String getPostTitle() {
	return postTitle;
    }

    /**
     * @param postContent the postContent to set
     */
    public void setPostTitle(String postTitle) {
	this.postTitle = postTitle;
    }

    /**
     * @return the postDescription
     */
    public String getPostDescription() {
	return postDescription;
    }

    /**
     * @param postDescription the postDescription to set
     */
    public void setPostDescription(String postDescription) {
	this.postDescription = postDescription;
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

}
