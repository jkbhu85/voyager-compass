package com.jk.travel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jk.core.util.DateWrapper;
import com.jk.core.util.LoggerManager;
import com.jk.travel.model.Visa;

public class VisaDAO extends AbstractDAO {
	public boolean addVisa(Visa visa) {
		boolean status = false;
		Connection con = null;

		try {
			// int visaId = getSequenceID("Emp_Visas", "ev_id");

			con = getConnection();
			String sql = "INSERT INTO Emp_Visas VALUES (VISA_ID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			// pstmt.setInt(1, visaId);
			int col = 1;
			pstmt.setInt(col++, visa.getPptId());
			pstmt.setInt(col++, visa.getVisaTypeId());
			pstmt.setString(col++, visa.getVisaNo());
			pstmt.setString(col++, DateWrapper.parseDate(visa.getIssueDate()));
			pstmt.setString(col++, DateWrapper.parseDate(visa.getExpireDate()));
			pstmt.setInt(col++, visa.getMaxVisits());
			pstmt.setInt(col++, visa.getVisitCount());
			pstmt.setString(col++, visa.getPlaceIssued());
			pstmt.setString(col++, visa.getComments());
			// setting the followint two because if a visa is being
			// added then it would not be a cancelled visa
			pstmt.setInt(col++, 0); // visa cancel status
			pstmt.setNull(col++, Types.DATE); // visa cancel date
			pstmt.setString(col++, visa.getVisaCost());

			status = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}


	public boolean updateVisa(Visa visa) {
		boolean status = false;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "UPDATE Emp_Visas ppt_ev_fk=?, vt_ev_fk=?, ev_number=?, "
					+ " ev_issue_date=?, ev_expire_date=?, ev_expire_date=?, ev_visit_count=?, "
					+ " ev_place_of_issue=?, ev_comments=?, ev_cancel_status=?, "
					+ " ev_cancel_date=?, ev_cost=? WHERE ev_id=?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(13, visa.getVisaId());
			pstmt.setInt(1, visa.getPptId());
			pstmt.setInt(2, visa.getVisaTypeId());
			pstmt.setString(3, visa.getVisaNo());
			pstmt.setString(4, DateWrapper.parseDate(visa.getIssueDate()));
			pstmt.setString(5, DateWrapper.parseDate(visa.getExpireDate()));
			pstmt.setInt(6, visa.getMaxVisits());
			pstmt.setInt(7, visa.getVisitCount());
			pstmt.setString(8, visa.getPlaceIssued());
			pstmt.setString(9, visa.getComments());
			pstmt.setInt(10, visa.getCancelStatus());
			pstmt.setString(11, DateWrapper.parseDate(visa.getCancelDate()));
			pstmt.setString(12, visa.getVisaCost());

			status = pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}


	/**
	 * Returns an instance of {@link Visa} if data exist for the specified visa
	 * id {@code null} otherwise
	 * 
	 * @param visaId
	 *            id for which to search data
	 * @return an instance of {@link Visa} if data exist for the specified visa
	 *         id {@code null} otherwise.
	 */
	public Visa getVisa(int visaId) {
		Connection con = null;
		Visa visa = null;

		try {
			con = getConnection();
			String sql = "SELECT ev.*,pp.emp_ppt_fk FROM Emp_Visas ev INNER JOIN Passports pp ON "
					+ " ev.ppt_ev_fk = pp.ppt_id WHERE ev.ev_id=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, visaId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
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
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return visa;
	}


	/**
	 * Returns a list of all visas associated with the specified passport id.
	 * This method never returns null.
	 * 
	 * @param pptId
	 *            passport id for which to search the data
	 * @return list of all visas associated with the specified passport id
	 */
	public List<Visa> getPptVisas(int pptId) {
		List<Visa> list = new ArrayList<>();
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT ev.*,pp.emp_ppt_fk FROM Emp_Visas ev INNER JOIN Passports pp ON "
					+ " ev.ppt_ev_fk = pp.ppt_id WHERE ev.ppt_ev_fk=? order by ev.ev_issue_date desc";

			String sql1 = " SELECT ev.*,pp.emp_ppt_fk,vt.vt_abbr,cm.cnt_name "
					+ " FROM Emp_Visas ev "
					+ " INNER JOIN Passports pp ON ev.ppt_ev_fk = pp.ppt_id "
					+ " INNER JOIN visa_types vt on vt.vt_id = ev.vt_ev_fk "
					+ " INNER JOIN countries_master cm on cm.cnt_id = vt.cnt_vt_fk "
					+ " WHERE ev.ppt_ev_fk=? order by cm.cnt_name asc, ev.ev_issue_date desc";

			PreparedStatement pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, pptId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Visa visa = new Visa();

				visa.setVisaId(rs.getInt("ev_id"));
				visa.setPptId(rs.getInt("ppt_ev_fk"));
				visa.setVisaTypeId(rs.getInt("vt_ev_fk"));
				visa.setVisaNo(rs.getString("ev_number"));
				visa.setIssueDate(DateWrapper.getDateString(rs.getDate("ev_issue_date")));
				visa.setExpireDate(DateWrapper.getDateString(rs.getDate("ev_expire_date")));
				visa.setMaxVisits(rs.getInt("ev_max_visits"));
				visa.setVisitCount(rs.getInt("ev_visit_count"));
				visa.setPlaceIssued(rs.getString("ev_place_of_issue"));
				visa.setComments(rs.getString("ev_comments"));
				visa.setCancelStatus(rs.getInt("ev_cancel_status"));
				visa.setCancelDate(DateWrapper.getDateString(rs.getDate("ev_cancel_date")));
				visa.setVisaCost(rs.getString("ev_cost"));
				visa.setEmpId(rs.getInt("emp_ppt_fk"));
				visa.setVisaTypeName(rs.getString("vt_abbr"));
				visa.setVisaCountry(rs.getString("cnt_name"));

				list.add(visa);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}


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
	public Visa getTypeVisa(int pptId, int visaTypeId) {
		Visa visa = null;
		Connection con = null;

		try {
			con = getConnection();
			String sql = "SELECT ev.*,pp.emp_ppt_fk FROM Emp_Visas ev INNER JOIN Passports pp ON "
					+ " pp.ppt_id=ev.ppt_ev_fk WHERE pp.ppt_ev_fk=? AND ev.vt_ev_fk=? order by ev.ev_issue_date desc";

			PreparedStatement pstmt = con.prepareStatement(sql);
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
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return visa;
	}


	public boolean visaExist(int visaId) {
		Connection con = null;
		boolean status = false;

		try {
			con = getConnection();
			String sql = "SELECT ppt_ev_fk from Emp_Visas where ev_id=?";
			PreparedStatement queryVisa = con.prepareStatement(sql);
			queryVisa.setInt(1, visaId);

			ResultSet rs = queryVisa.executeQuery();

			if (rs.next()) {
				status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}


	public int getVisaIdFromPpt(int pptId) {
		Connection con = null;
		int visaId = 0;

		try {
			con = getConnection();
			String sql = "SELECT ev_id from Emp_Visas where ppt_ev_fk=?";
			PreparedStatement queryVisa = con.prepareStatement(sql);
			queryVisa.setInt(1, pptId);

			ResultSet rs = queryVisa.executeQuery();

			if (rs.next()) {
				visaId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return visaId;
	}
}
