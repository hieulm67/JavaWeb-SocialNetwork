/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tbluser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import hieulm.util.DBHelpers;
import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblUserDAO implements Serializable {
    
    final String ACTIVE_ACCOUNT_STATUS_ID = "A";

    public TblUserDTO checkLogin(String email, String password) throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT userEmail, userName, password, userStatus "
			+ "FROM tblUser "
			+ "WHERE userEmail = ? AND password = ?");
		pst.setString(1, email);
		pst.setString(2, password);

		rs = pst.executeQuery();

		if (rs.next()) {
		    String name = rs.getString("userName");
		    String status = rs.getString("userStatus");

		    TblUserDTO dto = new TblUserDTO(email, name, password, status);
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

    public boolean checkEmail(String email) throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT userEmail "
			+ "FROM tblUser "
			+ "WHERE userEmail = ?");
		pst.setString(1, email);

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

    public boolean insertAccount(String email, String name, String password) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO tblUser(userEmail, userName, password) "
			+ "VALUES(?, ?, ?)");
		pst.setString(1, email);
		pst.setString(2, name);
		pst.setString(3, password);
		int result = pst.executeUpdate();

		if (result == 1) {
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

    public boolean activeAccount(String email) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblUser "
			+ "SET userStatus = ? "
			+ "WHERE userEmail = ?");
		pst.setString(1, ACTIVE_ACCOUNT_STATUS_ID);
		pst.setString(2, email);
		int result = pst.executeUpdate();

		if (result == 1) {
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

    public static String encryptePassword(String password) throws NoSuchAlgorithmException {
	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	byte[] encodeHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < encodeHash.length; i++) {
	    String hex = Integer.toHexString(0xff & encodeHash[i]);
	    if (hex.length() == 1) {
		hexString.append("0");
	    }
	    hexString.append(hex);
	}
	return hexString.toString();
    }
}
