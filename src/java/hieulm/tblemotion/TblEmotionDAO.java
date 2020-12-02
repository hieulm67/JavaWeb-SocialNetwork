/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblemotion;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblEmotionDAO implements Serializable {

    final String ACTIVE_EMOTION_STATUS_NAME = "Active";
    final String DEACTIVE_EMOTION_STATUS_ID = "D";
    final String EMOTION_TYPE_LIKE = "Like";
    final String EMOTION_TYPE_DISLIKE = "Dislike";

    public boolean insertEmotion(int postId, String userEmail, String emotionType) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblEmotion(postId, userEmail, emotionDate, emotionType) "
			+ "VALUES(?, ?, ?, ?)");
		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		pst.setTimestamp(3, timestamp);

		pst.setString(4, emotionType);
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

    public boolean updateEmotionStatus(int postId, String userEmail, String emotionType, String emotionStatus) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblEmotion "
			+ "SET emotionDate = ?, emotionStatus = ? "
			+ "WHERE postId = ? AND userEmail = ? AND emotionType = ?");

		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		pst.setTimestamp(1, timestamp);

		pst.setString(2, emotionStatus);
		pst.setInt(3, postId);
		pst.setString(4, userEmail);
		pst.setString(5, emotionType);

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

    public TblEmotionDTO getEmotionPostActive(int postId, String userEmail) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT emotionId, postId, userEmail, emotionDate, emotionType "
			+ "FROM tblEmotion JOIN tblStatus ON tblEmotion.emotionStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND userEmail = ? AND statusName = ?");

		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		pst.setString(3, ACTIVE_EMOTION_STATUS_NAME);

		rs = pst.executeQuery();
		if (rs.next()) {
		    int emotionId = rs.getInt("emotionId");
		    String emotionType = rs.getString("emotionType");
		    String emotionDate = rs.getString("emotionDate");

		    TblEmotionDTO dto = new TblEmotionDTO(emotionId, postId, userEmail, emotionDate, emotionType);
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

    public boolean checkEmotionActive(int postId, String userEmail) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT emotionId "
			+ "FROM tblEmotion JOIN tblStatus ON tblEmotion.emotionStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND userEmail = ? AND statusName = ?");
		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		pst.setString(3, ACTIVE_EMOTION_STATUS_NAME);
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

    public boolean checkEmotionByType(int postId, String userEmail, String emotionType) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT emotionId "
			+ "FROM tblEmotion "
			+ "WHERE postId = ? AND userEmail = ? AND emotionType = ?");
		pst.setInt(1, postId);
		pst.setString(2, userEmail);
		pst.setString(3, emotionType);
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

    public long calculateLikeEmotionByPost(int postId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	long row = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT COUNT(emotionId) AS totalLike "
			+ "FROM tblEmotion JOIN tblStatus ON tblEmotion.emotionStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND emotionType = ? AND statusName = ?");
		pst.setInt(1, postId);
		pst.setString(2, EMOTION_TYPE_LIKE);
		pst.setString(3, ACTIVE_EMOTION_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    row = rs.getLong("totalLike");
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
	return row;
    }

    public long calculateDislikeEmotionByPost(int postId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	long row = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT COUNT(emotionId) AS totalDislike "
			+ "FROM tblEmotion JOIN tblStatus ON tblEmotion.emotionStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND emotionType = ? AND statusName = ?");
		pst.setInt(1, postId);
		pst.setString(2, EMOTION_TYPE_DISLIKE);
		pst.setString(3, ACTIVE_EMOTION_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    row = rs.getLong("totalDislike");
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
	return row;
    }

    public void removeEmotionActiveByPostId(int postId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblEmotion "
			+ "SET emotionStatus = ? "
			+ "WHERE postId = ?");

		pst.setInt(2, postId);
		pst.setString(1, DEACTIVE_EMOTION_STATUS_ID);
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

}
