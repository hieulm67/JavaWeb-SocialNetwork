/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblcomment;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblCommentDAO implements Serializable{
    
    final String ACTIVE_COMMENT_STATUS_NAME = "Active";
    final String DEACTIVE_COMMENT_STATUS_ID = "D";
    
    public boolean insertComment(int postId, String userEmail, String commentContent) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblComment(postId, userEmail, commentContent) "
			+ "VALUES(?, ?, ?)");
		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		pst.setString(3, commentContent);
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
    
    private List<TblCommentDTO> listComment;

    public List<TblCommentDTO> getListComment() {
	return listComment;
    }
    
    public void searchCommentByPostId(int postId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT commentId, postId, userEmail, commentContent, commentDate "
			+ "FROM tblComment JOIN tblStatus ON tblComment.commentStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND statusName = ? "
			+ "ORDER BY commentDate DESC");
		pst.setInt(1, postId);
		pst.setString(2, ACTIVE_COMMENT_STATUS_NAME);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if(listComment == null){
			listComment = new ArrayList<>();
		    }
		    
		    int commentId = rs.getInt("commentId");
		    String userEmail = rs.getString("userEmail");
		    String commentContent = rs.getString("commentContent");
		    Timestamp commentDate = rs.getTimestamp("commentDate");
		    
		    TblCommentDTO dto = new TblCommentDTO(commentId, postId, userEmail, commentContent, formatDate(commentDate));
		    listComment.add(dto);
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
    
    public TblCommentDTO searchCommentByCommentId(int commentId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT commentId, postId, userEmail, commentContent, commentDate "
			+ "FROM tblComment JOIN tblStatus ON tblComment.commentStatus = tblStatus.statusId "
			+ "WHERE commentId = ? AND statusName = ?");
		pst.setInt(1, commentId);
		pst.setString(2, ACTIVE_COMMENT_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    int postId = rs.getInt("postId");
		    String userEmail = rs.getString("userEmail");
		    String commentContent = rs.getString("commentContent");
		    Timestamp commentDate = rs.getTimestamp("commentDate");
		    
		    TblCommentDTO dto = new TblCommentDTO(commentId, postId, userEmail, commentContent, formatDate(commentDate));
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
    
    public boolean removeCommentByCommentId(int commentId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblComment "
			+ "SET commentStatus = ? "
			+ "WHERE commentId = ?");
		pst.setString(1, DEACTIVE_COMMENT_STATUS_ID);
		pst.setInt(2, commentId);
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
    
    public void removeCommentByPostId(int postId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblComment "
			+ "SET commentStatus = ? "
			+ "WHERE postId = ?");
		pst.setString(1, DEACTIVE_COMMENT_STATUS_ID);
		pst.setInt(2, postId);
		int row = pst.executeUpdate();
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }
    
    private String formatDate(Timestamp commentDate){
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	return formatter.format(commentDate);
    }
}
