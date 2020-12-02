/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tbluser;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblUserCreateNewError implements Serializable{
    private String emailFormatError;
    private String nameLengthError;
    private String passwordLengthError;
    private String confirmNoMatchedError;
    private String emailIsExisted;

    /**
     * @return the emailFormatError
     */
    public String getEmailFormatError() {
	return emailFormatError;
    }

    /**
     * @param emailFormatError the emailFormatError to set
     */
    public void setEmailFormatError(String emailFormatError) {
	this.emailFormatError = emailFormatError;
    }

    /**
     * @return the nameLengthError
     */
    public String getNameLengthError() {
	return nameLengthError;
    }

    /**
     * @param nameLengthError the nameLengthError to set
     */
    public void setNameLengthError(String nameLengthError) {
	this.nameLengthError = nameLengthError;
    }

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
	return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
	this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the confirmNoMatchedError
     */
    public String getConfirmNoMatchedError() {
	return confirmNoMatchedError;
    }

    /**
     * @param confirmNoMatchedError the confirmNoMatchedError to set
     */
    public void setConfirmNoMatchedError(String confirmNoMatchedError) {
	this.confirmNoMatchedError = confirmNoMatchedError;
    }

    /**
     * @return the emailIsExisted
     */
    public String getEmailIsExisted() {
	return emailIsExisted;
    }

    /**
     * @param emailIsExisted the emailIsExisted to set
     */
    public void setEmailIsExisted(String emailIsExisted) {
	this.emailIsExisted = emailIsExisted;
    }
    
    
}
