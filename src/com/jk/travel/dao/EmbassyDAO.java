
package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Embassy;

public class EmbassyDAO extends AbstractDAO {

	public Connection con;
	private boolean flag = false;


	// insert embassy
	public boolean insertEmbassy(Embassy embassy) {
		// int embassyID = getSequenceID("ForignEmbassyMaster", "EmbassyID");

		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con
					.prepareStatement(
							"insert into ForignEmbassyMaster values(EMBS_ID_SEQ.nextval"
									+ ",?,?,?,?,?)");
			// pstmt.setInt(1, embassyID);
			int col = 1;
			pstmt.setInt(col++, embassy.getCountryID());
			pstmt.setString(col++, embassy.getEmbassyName());
			pstmt.setString(col++, embassy.getEmbassyAddr());
			pstmt.setString(col++, embassy.getEmbassyContactPerson());
			pstmt.setString(col++, embassy.getPhoneNo());
			int i = pstmt.executeUpdate();

			if (i > 0) {
				flag = true;
				con.commit();
			}
			else {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return flag;
	}


	// updateEmbassy
	public boolean updateEmbassy(Embassy embassy) {
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(
					"update ForignEmbassyMaster set countryID=?,EmbassyName=?,EmbassyAddress=?,EmbassyContactPerName=?,EmbassyPhoneNo=? where EmbassyID=?");

			pstmt.setInt(1, embassy.getCountryID());
			pstmt.setString(2, embassy.getEmbassyName());
			pstmt.setString(3, embassy.getEmbassyAddr());
			pstmt.setString(4, embassy.getEmbassyContactPerson());
			pstmt.setString(5, embassy.getPhoneNo());
			pstmt.setInt(6, embassy.getEmbassyID());
			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
				con.commit();
			}
			else {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return flag;
	}


	public List<Embassy> getEmbassyList() {
		List<Embassy> list = new ArrayList<>();

		try {
			con = getConnection();

			Statement st = con.createStatement();
			String sql = "SELECT e.* FROM ForignEmbassyMaster e INNER JOIN Countries_Master c ON "
					+ " e.countryid=c.cnt_id ORDER BY upper(c.cnt_name)";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Embassy embassy = new Embassy();
				embassy.setEmbassyID(rs.getInt(1));
				embassy.setCountryID(rs.getInt(2));
				embassy.setEmbassyName(rs.getString(3));
				embassy.setEmbassyAddr(rs.getString(4));
				embassy.setEmbassyContactPerson(rs.getString(5));
				embassy.setPhoneNo(rs.getString(6));

				list.add(embassy);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return list;
	}


	// select Particular Embassy
	public Embassy getEmbassy(int embassyno) {
		Statement st;
		Embassy embassy = null;
		try {
			con = getConnection();
			embassy = new Embassy();
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * from ForignEmbassyMaster where EmbassyID=" + embassyno);
			if (rs.next()) {

				embassy.setEmbassyID(rs.getInt(1));
				embassy.setCountryID(rs.getInt(2));
				embassy.setEmbassyName(rs.getString(3));
				embassy.setEmbassyAddr(rs.getString(4));
				embassy.setEmbassyContactPerson(rs.getString(5));
				embassy.setPhoneNo(rs.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return embassy;
	}

}
