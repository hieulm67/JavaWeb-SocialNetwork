/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblarticle;

import hieulm.util.DBHelpers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.Part;

/**
 *
 * @author MinHiu
 */
public class TblArticleDAO implements Serializable {

    final int ARTICLE_PER_PAGE = 20;
    final String ACTIVE_ARTICLE_STATUS_NAME = "Active";
    final String DEACTIVE_ARTICLE_STATUS_ID = "D";

    private int numberOfPage;

    public int getNumberOfPage() {
	return numberOfPage;
    }

    public void calculateNumberOfPage(String content) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	int row = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT postId "
			+ "FROM tblArticle JOIN tblStatus ON tblArticle.postStatus = tblStatus.statusId "
			+ "WHERE postDescription LIKE ? AND statusName = ?");
		pst.setString(1, "%" + content + "%");
		pst.setString(2, ACTIVE_ARTICLE_STATUS_NAME);
		rs = pst.executeQuery();
		while (rs.next()) {
		    row++;
		}

		if ((row % ARTICLE_PER_PAGE) != 0) {
		    numberOfPage = (row / ARTICLE_PER_PAGE) + 1;
		} else {
		    numberOfPage = row / ARTICLE_PER_PAGE;
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

    public boolean insertArticle(Part imagePart, String postTitle, String postDescription, String userEmail) throws NamingException, SQLException, IOException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblArticle(image, postTitle, postDescription, userEmail) "
			+ "VALUES(?, ?, ?, ?)");
		pst.setBinaryStream(1, imagePart.getInputStream(), (int) imagePart.getSize());
		pst.setString(2, postTitle);
		pst.setString(3, postDescription);
		pst.setString(4, userEmail);

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

    private List<TblArticleDTO> listArticle;

    public List<TblArticleDTO> getListArticle() {
	return listArticle;
    }

    public void getListArticleByPage(String content, int pageNumber) throws NamingException, SQLException, IOException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT postId, image, datePost, postTitle, postDescription, userEmail "
			+ "FROM tblArticle JOIN tblStatus ON tblArticle.postStatus = tblStatus.statusId "
			+ "WHERE postDescription LIKE ? AND statusName = ? "
			+ "ORDER BY datePost DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT ? ROWS ONLY");
		pst.setString(1, "%" + content + "%");
		pst.setString(2, ACTIVE_ARTICLE_STATUS_NAME);
		pst.setInt(3, (pageNumber - 1) * ARTICLE_PER_PAGE);
		pst.setInt(4, ARTICLE_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listArticle == null) {
			listArticle = new ArrayList<>();
		    }

		    int postId = rs.getInt("postId");

		    InputStream inputStream = rs.getBinaryStream("image");

		    String base64Image = encodeImageToBase64String(inputStream);
		    inputStream.close();

		    Timestamp datePost = rs.getTimestamp("datePost");
		    String postContent = rs.getString("postTitle");
		    String description = rs.getString("postDescription");
		    String userEmail = rs.getString("userEmail");

		    TblArticleDTO dto = new TblArticleDTO(postId, base64Image, formatDate(datePost), postContent, description, userEmail);
		    listArticle.add(dto);
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

    public TblArticleDTO searchArticle(int postId) throws NamingException, SQLException, IOException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT postId, image, datePost, postTitle, postDescription, userEmail "
			+ "FROM tblArticle JOIN tblStatus ON tblArticle.postStatus = tblStatus.statusId "
			+ "WHERE postId = ? AND statusName = ?");
		pst.setInt(1, postId);
		pst.setString(2, ACTIVE_ARTICLE_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    InputStream inputStream = rs.getBinaryStream("image");
		    String base64Image = encodeImageToBase64String(inputStream);
		    inputStream.close();

		    Timestamp datePost = rs.getTimestamp("datePost");
		    String postContent = rs.getString("postTitle");
		    String description = rs.getString("postDescription");
		    String userEmail = rs.getString("userEmail");

		    TblArticleDTO dto = new TblArticleDTO(postId, base64Image, formatDate(datePost), postContent, description, userEmail);
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

    public boolean deleteArticle(int postId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblArticle "
			+ "SET postStatus = ? "
			+ "WHERE postId = ?");
		pst.setString(1, DEACTIVE_ARTICLE_STATUS_ID);
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

    private String encodeImageToBase64String(InputStream inputStream) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	byte[] buffer = new byte[4096];
	int bytesRead = -1;
	while ((bytesRead = inputStream.read(buffer)) != -1) {
	    outputStream.write(buffer, 0, bytesRead);
	}
	byte[] imageBytes = outputStream.toByteArray();
	String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	outputStream.close();

	return base64Image;
    }

    private String formatDate(Timestamp postDate) {
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	return formatter.format(postDate);
    }
}
