package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.LoggerManager;
import com.jk.travel.model.VisaType;

public class VisaTypeDAO extends AbstractDAO {
	
	private static final String SQL_INSERT_VISA_TYPE = ""
			+ "INSERT INTO VISA_TYPES VALUES(?,?,?,?,?,?,?,?)";
	
	public boolean insertVisaType(VisaType visaType) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_VISA_TYPE);

			int col = 1;
			pstmt.setInt(col++, visaType.getCntVtFk());
			pstmt.setString(col++, visaType.getVisaTypeName());
			pstmt.setString(col++, visaType.getVisaTypeAbbr());
			pstmt.setString(col++, visaType.getVisaTypeDesc());
			pstmt.setString(col++, visaType.getVisaEleg());
			pstmt.setInt(col++, visaType.getMaxDuration());
			pstmt.setString(col++, visaType.getStampGuide());
			pstmt.setInt(col++, visaType.getReqAdv());

			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return false;
	}

	private static final String SQL_UPDATE_VISA_TYPE = ""
			+ " UPDATE VISA_TYPES "
			+ " SET "
			+ "   CNT_VT_FK=?,VT_NAME=?,VT_ABBR=?, "
			+ "   VT_DESC=?,VT_ELEG=?,VT_STAY_MAX_DUR=?, "
			+ "   VT_STAMP_GUIDE=?,VT_REQUIRED_ADV=? "
			+ " WHERE VT_ID=? ";

	public boolean updateVisaType(VisaType visaType) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_VISA_TYPE);

			pstmt.setInt(1, visaType.getCntVtFk());
			pstmt.setString(2, visaType.getVisaTypeName());
			pstmt.setString(3, visaType.getVisaTypeAbbr());
			pstmt.setString(4, visaType.getVisaTypeDesc());
			pstmt.setString(5, visaType.getVisaEleg());
			pstmt.setInt(6, visaType.getMaxDuration());
			pstmt.setString(7, visaType.getStampGuide());
			pstmt.setInt(8, visaType.getReqAdv());
			pstmt.setInt(9, visaType.getVisaTypeID());

			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return false;
	}

	private static final String SQL_FIND_ALL_VISA_TYPES = ""
			+ " SELECT "
			+ "   V.* "
			+ " FROM "
			+ "   VISA_TYPES V "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON V.CNT_VT_FK=C.CNT_ID "
			+ " ORDER BY "
			+ "    C.CNT_NAME ASC, "
			+ "    V.VT_NAME";

	/**
	 * Returns list of all visa types available sorted by country names and by
	 * visa names.
	 * 
	 * @return list of visa types; never returns null value.
	 */
	public List<VisaType> getVisaTypeList() {
		Connection con = null;
		List<VisaType> list = new ArrayList<>();

		try {
			con = getConnection();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_FIND_ALL_VISA_TYPES);

			while (rs.next()) {
				VisaType visa = new VisaType();

				visa.setVisaTypeID(rs.getInt(1));
				visa.setCntVtFk(rs.getInt(2));
				visa.setVisaTypeName(rs.getString(3));
				visa.setVisaTypeAbbr(rs.getString(4));
				visa.setVisaTypeDesc(rs.getString(5));
				visa.setVisaEleg(rs.getString(6));
				visa.setMaxDuration(rs.getInt(7));
				visa.setStampGuide(rs.getString(8));
				visa.setReqAdv(rs.getInt(9));

				list.add(visa);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}


	private static final String SQL_FIND_ALL_VISA_TYPES_BY_COUNTRY_ID = ""
			+ " SELECT "
			+ "   V.* "
			+ " FROM "
			+ "   VISA_TYPES V "
			+ "   INNER JOIN COUNTRIES_MASTER C "
			+ "     ON V.CNT_VT_FK=C.CNT_ID "
			+ " WHERE "
			+ "   V.CNT_VT_FK=? "
			+ " ORDER BY "
			+ "   V.VT_NAME";

	/**
	 * Returns list of all visa types available for a country sorted by visa
	 * names.
	 * 
	 * @param countryId
	 *            id of the country to filter visa types
	 * @return list of visa types; never returns null value.
	 */
	public List<VisaType> findAllVisaTypesByCountryId(int countryId) {
		Connection con = null;
		List<VisaType> list = new ArrayList<>();

		try {
			con = getConnection();

			PreparedStatement st = con.prepareStatement(SQL_FIND_ALL_VISA_TYPES_BY_COUNTRY_ID);
			st.setInt(1, countryId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				VisaType visa = new VisaType();

				visa.setVisaTypeID(rs.getInt(1));
				visa.setCntVtFk(rs.getInt(2));
				visa.setVisaTypeName(rs.getString(3));
				visa.setVisaTypeAbbr(rs.getString(4));
				visa.setVisaTypeDesc(rs.getString(5));
				visa.setVisaEleg(rs.getString(6));
				visa.setMaxDuration(rs.getInt(7));
				visa.setStampGuide(rs.getString(8));
				visa.setReqAdv(rs.getInt(9));

				list.add(visa);
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return list;
	}


	private static final String SQL_FIND_VISA_TYPE_BY_ID = ""
			+ "SELECT * FROM VISA_TYPES WHERE VT_ID=?";

	public VisaType getVisaType(int visaTypeId) {
		Connection con = null;
		VisaType visa = null;

		try {
			con = getConnection();
			visa = new VisaType();
			PreparedStatement st = con.prepareStatement(SQL_FIND_VISA_TYPE_BY_ID);
			st.setInt(1, visaTypeId);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				visa.setVisaTypeID(rs.getInt(1));
				visa.setCntVtFk(rs.getInt(2));
				visa.setVisaTypeName(rs.getString(3));
				visa.setVisaTypeAbbr(rs.getString(4));
				visa.setVisaTypeDesc(rs.getString(5));
				visa.setVisaEleg(rs.getString(6));
				visa.setMaxDuration(rs.getInt(7));
				visa.setStampGuide(rs.getString(8));
				visa.setReqAdv(rs.getInt(9));
			}
		} catch (Exception e) {
			LoggerManager.writeLogWarning(e);
		} finally {
			DaoUtils.closeCon(con);
		}
		return visa;
	}

}
