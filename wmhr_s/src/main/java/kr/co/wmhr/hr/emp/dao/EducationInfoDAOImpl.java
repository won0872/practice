package kr.co.wmhr.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.emp.to.EducationInfoTO;

public class EducationInfoDAOImpl implements EducationInfoDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<EducationInfoTO> selectEducationList(String code) {

		ArrayList<EducationInfoTO> educationlist = new ArrayList<EducationInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			EducationInfoTO educationInfo = null;
			query.append("select emp_code, education_code, school_name, major,grade, ");
			query.append(
					"to_char(entrance_date,'YYYY/MM/DD') entrance_date,to_char(graduate_date,'YYYY/MM/DD') graduate_date ");
			query.append("from education_info ");
			query.append("where emp_code = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				educationInfo = new EducationInfoTO();
				educationInfo.setEmpCode(rs.getString("emp_code"));
				educationInfo.setEducationCode(rs.getString("education_code"));
				educationInfo.setSchoolName(rs.getString("school_name"));
				educationInfo.setMajor(rs.getString("major"));
				educationInfo.setEntranceDate(rs.getString("entrance_date"));
				educationInfo.setGraduateDate(rs.getString("graduate_date"));
				educationInfo.setGrade(rs.getString("grade"));
				educationlist.add(educationInfo);
			}

			return educationlist;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertEducationInfo(EducationInfoTO educationInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(
					"INSERT INTO EDUCATION_INFO VALUES (?, EDUCATION_CODE_SEQ.NEXTVAL, ?, ?, to_date(?,'YYYY/MM/DD'), to_date(?,'YYYY/MM/DD'), ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, educationInfo.getEmpCode());
			pstmt.setString(2, educationInfo.getSchoolName());
			pstmt.setString(3, educationInfo.getMajor());
			pstmt.setString(4, educationInfo.getEntranceDate());
			pstmt.setString(5, educationInfo.getGraduateDate());
			pstmt.setString(6, educationInfo.getGrade());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void updateEducationInfo(EducationInfoTO educationInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("UPDATE EDUCATION_INFO SET ");
			query.append("SCHOOL_NAME= ? , MAJOR = ?, ENTRANCE_DATE = to_date(?,'YYYY/MM/DD'), ");
			query.append(
					"GRADUATE_DATE = to_date(?,'YYYY/MM/DD'), GRADE = ? WHERE EMP_CODE = ? and EDUCATION_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, educationInfo.getSchoolName());
			pstmt.setString(2, educationInfo.getMajor());
			pstmt.setString(3, educationInfo.getEntranceDate());
			pstmt.setString(4, educationInfo.getGraduateDate());
			pstmt.setString(5, educationInfo.getGrade());
			pstmt.setString(6, educationInfo.getEmpCode());
			pstmt.setString(7, educationInfo.getEducationCode());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteEducationInfo(EducationInfoTO educationInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EDUCATION_INFO WHERE EMP_CODE = ? AND EDUCATION_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, educationInfo.getEmpCode());
			pstmt.setString(2, educationInfo.getEducationCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
}

