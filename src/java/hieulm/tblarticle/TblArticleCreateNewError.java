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
public class TblArticleCreateNewError implements Serializable{
    
    private String postContentLengthError;
    private String postDescriptionLengthError;
    /**
     * @return the postContentLengthError
     */
    public String getPostContentLengthError() {
	return postContentLengthError;
    }

    /**
     * @param postContentLengthError the postContentLengthError to set
     */
    public void setPostContentLengthError(String postContentLengthError) {
	this.postContentLengthError = postContentLengthError;
    }

    /**
     * @return the postDescriptionLengthError
     */
    public String getPostDescriptionLengthError() {
	return postDescriptionLengthError;
    }

    /**
     * @param postDescriptionLengthError the postDescriptionLengthError to set
     */
    public void setPostDescriptionLengthError(String postDescriptionLengthError) {
	this.postDescriptionLengthError = postDescriptionLengthError;
    }

}
