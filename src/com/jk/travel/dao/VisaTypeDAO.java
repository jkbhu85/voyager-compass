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
	// insert visatype
	public boolean insertVisaType(VisaType visaType) {
		Connection con = null;
		boolean flag = false;
		// int visaID = getSequenceID("VISA_TYPES", "VT_ID");

		try {
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement(
							"insert into VISA_TYPES values(VTYPE_ID_SEQ.nextval,?,?,?,?,?,?,?,?)");

			// pstmt.setInt(1, visaID);
			int col = 1;
			pstmt.setInt(col++, visaType.getCntVtFk());
			pstmt.setString(col++, visaType.getVisaTypeName());
			pstmt.setString(col++, visaType.getVisaTypeAbbr());
			pstmt.setString(col++, visaType.getVisaTypeDesc());
			pstmt.setString(col++, visaType.getVisaEleg());
			pstmt.setInt(col++, visaType.getMaxDuration());
			pstmt.setString(col++, visaType.getStampGuide());
			pstmt.setInt(col++, visaType.getReqAdv());

			int i = pstmt.executeUpdate();

			if (i > 0) flag = true;
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


	// updateVisaTypes
	public boolean updateVisaType(VisaType visaType) {
		Connection con = null;
		boolean flag = false;

		try {
			con = getConnection();
			String sql = "update visa_types set cnt_vt_fk=?,vt_name=?,vt_abbr=?,"
					+ " vt_desc=?,vt_eleg=?,vt_stay_max_dur=?,vt_stamp_guide=?,vt_required_adv=? where vt_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, visaType.getCntVtFk());
			pstmt.setString(2, visaType.getVisaTypeName());
			pstmt.setString(3, visaType.getVisaTypeAbbr());
			pstmt.setString(4, visaType.getVisaTypeDesc());
			pstmt.setString(5, visaType.getVisaEleg());
			pstmt.setInt(6, visaType.getMaxDuration());
			pstmt.setString(7, visaType.getStampGuide());
			pstmt.setInt(8, visaType.getReqAdv());
			pstmt.setInt(9, visaType.getVisaTypeID());

			int i = pstmt.executeUpdate();

			if (i > 0) flag = true;
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
			String sql = "select v.* from visa_types v inner join countries_master c on "
					+ " v.cnt_vt_fk=c.cnt_id order by c.cnt_name asc, v.vt_name";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

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


	/**
	 * Returns list of all visa types available for a country sorted by visa
	 * names.
	 * 
	 * @param countryFilter
	 *            id of the country to filter visa types
	 * @returnlist of visa types; never returns null value.
	 */
	public List<VisaType> getVisaTypeList(int countryFilter) {
		Connection con = null;
		List<VisaType> list = new ArrayList<>();

		try {
			con = getConnection();
			String sql = "select v.* from visa_types v inner join countries_master c on "
					+ " v.cnt_vt_fk=c.cnt_id where v.cnt_vt_fk=? order by v.vt_name";

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, countryFilter);

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


	// select Particular Department
	public VisaType getVisaType(int visaTypeId) {
		Connection con = null;
		VisaType visa = null;

		try {
			con = getConnection();
			visa = new VisaType();
			String sql = "select * from visa_types where vt_id=?";
			PreparedStatement st = con.prepareStatement(sql);
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
			e.printStackTrace();
			LoggerManager.writeLogWarning(e);
		} finally {
			try {
				if (con != null) con.close();

			} catch (Exception e) {
			}
		}
		return visa;
	}

}
