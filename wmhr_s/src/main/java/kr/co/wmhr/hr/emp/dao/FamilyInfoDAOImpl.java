package kr.co.wmhr.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.emp.to.FamilyInfoTO;

public class FamilyInfoDAOImpl implements FamilyInfoDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<FamilyInfoTO> selectFamilyList(String code) {

		ArrayList<FamilyInfoTO> familylist = new ArrayList<FamilyInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			FamilyInfoTO familyInfo = null;
			query.append("select emp_code, family_code, family_name, relation, ");
			query.append("to_char(birthdate,'YYYY/MM/DD') birthdate, live_together ");
			query.append("from family_info ");
			query.append("where emp_code = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				familyInfo = new FamilyInfoTO();
				familyInfo.setEmpCode(rs.getString("emp_code"));
				familyInfo.setFamilyCode(rs.getString("family_code"));
				familyInfo.setFamilyName(rs.getString("family_name"));
				familyInfo.setRelation(rs.getString("relation"));
				familyInfo.setBirthdate(rs.getString("birthdate"));
				familyInfo.setLiveTogether(rs.getString("live_together"));
				familylist.add(familyInfo);
			}
			return familylist;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertFamilyInfo(FamilyInfoTO familyInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(
					"INSERT INTO family_info VALUES (?, FAMILY_CODE_SEQ.NEXTVAL, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, familyInfo.getEmpCode());
			pstmt.setString(2, familyInfo.getFamilyName());
			pstmt.setString(3, familyInfo.getRelation());
			pstmt.setString(4, familyInfo.getBirthdate());
			pstmt.setString(5, familyInfo.getLiveTogether());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void updateFamilyInfo(FamilyInfoTO familyInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("UPDATE family_info SET ");
			query.append("FAMILY_NAME= ? , RELATION = ?, BIRTHDATE = TO_DATE(?, 'YYYY/MM/DD'), ");
			query.append("LIVE_TOGETHER = ? WHERE EMP_CODE = ? and FAMILY_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, familyInfo.getFamilyName());
			pstmt.setString(2, familyInfo.getRelation());
			pstmt.setString(3, familyInfo.getBirthdate());
			pstmt.setString(4, familyInfo.getLiveTogether());
			pstmt.setString(5, familyInfo.getEmpCode());
			pstmt.setString(6, familyInfo.getFamilyCode());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteFamilyInfo(FamilyInfoTO familyInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM family_info WHERE EMP_CODE = ? AND FAMILY_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, familyInfo.getEmpCode());
			pstmt.setString(2, familyInfo.getFamilyCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
}