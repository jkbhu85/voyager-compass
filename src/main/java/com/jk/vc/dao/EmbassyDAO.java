
package com.jk.vc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.vc.model.*;

public class EmbassyDAO extends AbstractDAO {
	
	private static final String SQL_INSERT_EMBASSY = ""
			+ "INSERT INTO FORIGNEMBASSYMASTER VALUES(?,?,?,?,?)";

	public boolean insertEmbassy(Embassy embassy) {
		Connection con = null;
		boolean flag = false;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_EMBASSY);
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
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	private static final String SQL_UPDATE_EMBASSY = ""
			+ " UPDATE FORIGNEMBASSYMASTER "
			+ " SET COUNTRYID=?,EMBASSYNAME=?,EMBASSYADDRESS=?,"
			+ " EMBASSYCONTACTPERNAME=?,EMBASSYPHONENO=? "
			+ " WHERE EMBASSYID=?";

	public boolean updateEmbassy(Embassy embassy) {
		Connection con = null;
		boolean flag = false;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_EMBASSY);

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
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return flag;
	}

	private static final String SQL_FIND_ALL_EMBASSIES = ""
			+ " SELECT "
			+ "   E.* "
			+ " FROM"
			+ "   FORIGNEMBASSYMASTER E "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON E.COUNTRYID=C.CNT_ID "
			+ " ORDER BY C.CNT_NAME";

	public List<Embassy> findAllEmbassies() {
		List<Embassy> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_EMBASSIES);

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
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}

	private static final String SQL_FIND_EMBASSY_BY_ID = ""
			+ "SELECT * FROM FORIGNEMBASSYMASTER WHERE EMBASSYID=?";
	
	public Embassy findEmbassyById(int embassyId) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(SQL_FIND_EMBASSY_BY_ID);
			ps.setInt(1, embassyId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Embassy embassy = new Embassy();
				embassy.setEmbassyID(rs.getInt(1));
				embassy.setCountryID(rs.getInt(2));
				embassy.setEmbassyName(rs.getString(3));
				embassy.setEmbassyAddr(rs.getString(4));
				embassy.setEmbassyContactPerson(rs.getString(5));
				embassy.setPhoneNo(rs.getString(6));
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return null;
	}

}
