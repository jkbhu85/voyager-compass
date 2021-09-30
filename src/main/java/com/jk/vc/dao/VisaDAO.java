package com.jk.vc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.*;
import com.jk.vc.model.*;
import com.jk.vc.util.*;


public class VisaDAO extends AbstractDAO {
	
	private static final String SQL_INSERT_VISA = ""
			+ "INSERT INTO EMP_VISAS VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public boolean insertVisa(Visa visa) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_VISA);

			int col = 1;
			pstmt.setInt(col++, visa.getPptId());
			pstmt.setInt(col++, visa.getVisaTypeId());
			pstmt.setString(col++, visa.getVisaNo());
			pstmt.setString(col++, DateUtils.convertDate(visa.getIssueDate()));
			pstmt.setString(col++, DateUtils.convertDate(visa.getExpireDate()));
			pstmt.setInt(col++, visa.getMaxVisits());
			pstmt.setInt(col++, visa.getVisitCount());
			pstmt.setString(col++, visa.getPlaceIssued());
			pstmt.setString(col++, visa.getComments());
			// setting the follow int two because if a visa is being
			// added then it would not be a cancelled visa
			pstmt.setInt(col++, 0); // visa cancel status
			pstmt.setNull(col++, Types.DATE); // visa cancel date
			pstmt.setString(col++, visa.getVisaCost());

			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return false;
	}

	
	private static final String SQL_UPDATE_VISA = ""
			+ " UPDATE EMP_VISAS PPT_EV_FK=?, VT_EV_FK=?, EV_NUMBER=?, "
			+ " EV_ISSUE_DATE=?, EV_EXPIRE_DATE=?, EV_EXPIRE_DATE=?, EV_VISIT_COUNT=?, "
			+ " EV_PLACE_OF_ISSUE=?, EV_COMMENTS=?, EV_CANCEL_STATUS=?, "
			+ " EV_CANCEL_DATE=?, EV_COST=? WHERE EV_ID=?";

	public boolean updateVisa(Visa visa) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_VISA);

			pstmt.setInt(1, visa.getPptId());
			pstmt.setInt(2, visa.getVisaTypeId());
			pstmt.setString(3, visa.getVisaNo());
			pstmt.setString(4, DateUtils.convertDate(visa.getIssueDate()));
			pstmt.setString(5, DateUtils.convertDate(visa.getExpireDate()));
			pstmt.setInt(6, visa.getMaxVisits());
			pstmt.setInt(7, visa.getVisitCount());
			pstmt.setString(8, visa.getPlaceIssued());
			pstmt.setString(9, visa.getComments());
			pstmt.setInt(10, visa.getCancelStatus());
			pstmt.setString(11, DateUtils.convertDate(visa.getCancelDate()));
			pstmt.setString(12, visa.getVisaCost());
			pstmt.setInt(13, visa.getVisaId());

			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return false;
	}


	private static final String SQL_FIND_VISA_BY_ID = ""
			+ " SELECT "
			+ "   EV.*, PP.EMP_PPT_FK "
			+ " FROM "
			+ "   EMP_VISAS EV "
			+ "   INNER JOIN PASSPORTS PP "
			+ "     ON EV.PPT_EV_FK = PP.PPT_ID "
			+ " WHERE EV.EV_ID=?";
	/**
	 * Returns an instance of {@link Visa} if data exist for the specified visa
	 * id {@code null} otherwise
	 * 
	 * @param visaId
	 *            id for which to search data
	 * @return an instance of {@link Visa} if data exist for the specified visa
	 *         id {@code null} otherwise.
	 */
	public Visa findVisaById(int visaId) {
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_VISA_BY_ID);
			pstmt.setInt(1, visaId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Visa visa = new Visa();

				visa.setVisaId(rs.getInt(1));
				visa.setPptId(rs.getInt(2));
				visa.setVisaTypeId(rs.getInt(3));
				visa.setVisaNo(rs.getString(4));
				visa.setIssueDate(rs.getString(5));
				visa.setExpireDate(rs.getString(6));
				visa.setMaxVisits(rs.getInt(7));
				visa.setVisitCount(rs.getInt(8));
				visa.setPlaceIssued(rs.getString(9));
				visa.setComments(rs.getString(10));
				visa.setCancelStatus(rs.getInt(11));
				visa.setCancelDate(rs.getString(12));
				visa.setCancelDate(rs.getString(13));
				visa.setEmpId(rs.getInt(14));
			}
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return null;
	}
	
	private static final String SQL_FIND_ALL_VISAS_BY_PASSPORT_ID = ""
			+ " SELECT "
			+ "   EV.*,PP.EMP_PPT_FK,VT.VT_ABBR,CM.CNT_NAME "
			+ " FROM "
			+ "   EMP_VISAS EV "
			+ "   INNER JOIN PASSPORTS PP "
			+ "     ON EV.PPT_EV_FK = PP.PPT_ID "
			+ "   INNER JOIN VISA_TYPES VT "
			+ "     ON VT.VT_ID = EV.VT_EV_FK "
			+ "   INNER JOIN COUNTRIES_MASTER CM "
			+ "     ON CM.CNT_ID = VT.CNT_VT_FK "
			+ " WHERE "
			+ "   EV.PPT_EV_FK=? "
			+ " ORDER BY "
			+ "   CM.CNT_NAME ASC, "
			+ "   EV.EV_ISSUE_DATE DESC ";

	/**
	 * Returns a list of all visas associated with the specified passport id.
	 * This method never returns null.
	 * 
	 * @param pptId
	 *            passport id for which to search the data
	 * @return list of all visas associated with the specified passport id
	 */
	public List<Visa> findAllVisasByPassportId(int pptId) {
		List<Visa> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(SQL_FIND_ALL_VISAS_BY_PASSPORT_ID);
			pstmt.setInt(1, pptId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Visa visa = new Visa();

				visa.setVisaId(rs.getInt("EV_ID"));
				visa.setPptId(rs.getInt("PPT_EV_FK"));
				visa.setVisaTypeId(rs.getInt("VT_EV_FK"));
				visa.setVisaNo(rs.getString("EV_NUMBER"));
				visa.setIssueDate(DateUtils.getDateString(rs.getDate("EV_ISSUE_DATE")));
				visa.setExpireDate(DateUtils.getDateString(rs.getDate("EV_EXPIRE_DATE")));
				visa.setMaxVisits(rs.getInt("EV_MAX_VISITS"));
				visa.setVisitCount(rs.getInt("EV_VISIT_COUNT"));
				visa.setPlaceIssued(rs.getString("EV_PLACE_OF_ISSUE"));
				visa.setComments(rs.getString("EV_COMMENTS"));
				visa.setCancelStatus(rs.getInt("EV_CANCEL_STATUS"));
				visa.setCancelDate(DateUtils.getDateString(rs.getDate("EV_CANCEL_DATE")));
				visa.setVisaCost(rs.getString("EV_COST"));
				visa.setEmpId(rs.getInt("EMP_PPT_FK"));
				visa.setVisaTypeName(rs.getString("VT_ABBR"));
				visa.setVisaCountry(rs.getString("CNT_NAME"));

				list.add(visa);
			}
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return list;
	}

	private static final String FIND_MOST_RECENT_VISA_BY_PASSPORT_ID_AND_VISA_TYPE = ""
			+ " SELECT "
			+ "   EV.*,PP.EMP_PPT_FK "
			+ " FROM "
			+ "   EMP_VISAS EV "
			+ "   INNER JOIN PASSPORTS PP "
			+ "     ON PP.PPT_ID=EV.PPT_EV_FK "
			+ " WHERE "
			+ "   PP.PPT_EV_FK=? "
			+ "   AND EV.VT_EV_FK=? "
			+ " ORDER BY "
			+ "   EV.EV_ISSUE_DATE DESC";

	/**
	 * Returns a most recent visa of specified type associated with specified
	 * passport id.
	 * 
	 * @param pptId
	 *            passport id associated with visa
	 * @param visaTypeId
	 *            type of the visa
	 * @return most recent visa of specified type associated with specified
	 *         passport id
	 */
	public Visa findMostRecentVisaByPassportIdAndVisaType(int pptId, int visaTypeId) {
		Visa visa = null;
		Connection con = null;

		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement(FIND_MOST_RECENT_VISA_BY_PASSPORT_ID_AND_VISA_TYPE);
			pstmt.setInt(1, pptId);
			pstmt.setInt(2, visaTypeId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				visa = new Visa();

				visa.setVisaId(rs.getInt(1));
				visa.setPptId(rs.getInt(2));
				visa.setVisaTypeId(rs.getInt(3));
				visa.setVisaNo(rs.getString(4));
				visa.setIssueDate(rs.getString(5));
				visa.setExpireDate(rs.getString(6));
				visa.setMaxVisits(rs.getInt(7));
				visa.setVisitCount(rs.getInt(8));
				visa.setPlaceIssued(rs.getString(9));
				visa.setComments(rs.getString(10));
				visa.setCancelStatus(rs.getInt(11));
				visa.setCancelDate(rs.getString(12));
				visa.setCancelDate(rs.getString(13));
				visa.setEmpId(rs.getInt(14));
			}
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return visa;
	}

	private static final String SQL_DOES_VISA_EXIST_BY_ID = ""
			+ "SELECT PPT_EV_FK FROM EMP_VISAS WHERE EV_ID=?";

	public boolean doesVisaExistById(int visaId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement queryVisa = con.prepareStatement(SQL_DOES_VISA_EXIST_BY_ID);
			queryVisa.setInt(1, visaId);

			ResultSet rs = queryVisa.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return false;
	}

	private static final String SQL_FIND_VISA_ID_BY_PASSPORT_ID = ""
			+ "SELECT EV_ID FROM EMP_VISAS WHERE PPT_EV_FK=?";

	public int findVisaIdFromPassportId(int pptId) {
		Connection con = null;

		try {
			con = getConnection();
			PreparedStatement queryVisa = con.prepareStatement(SQL_FIND_VISA_ID_BY_PASSPORT_ID);
			queryVisa.setInt(1, pptId);

			ResultSet rs = queryVisa.executeQuery();
			return rs.getInt(1);
		} catch (Exception e) {
			LoggerManager.writeLogSevere(e);
		} finally {
			DaoUtils.closeCon(con);
		}

		return 0;
	}
}
