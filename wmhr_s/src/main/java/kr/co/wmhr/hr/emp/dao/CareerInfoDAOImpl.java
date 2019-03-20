package kr.co.wmhr.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.emp.to.CareerInfoTO;

public class CareerInfoDAOImpl implements CareerInfoDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<CareerInfoTO> selectCareerList(String code) {

		ArrayList<CareerInfoTO> careerlist = new ArrayList<CareerInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			CareerInfoTO careerInfo = null;
			query.append("select emp_code,career_code,company_name,occupation,assignment_task, ");
			query.append(
					"TO_CHAR(ex_retirement_date,'YYYY/MM/DD') ex_retirement_date,TO_CHAR(ex_hiredate,'YYYY/MM/DD') ex_hiredate ");
			query.append("from career_info ");
			query.append("where emp_code = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				careerInfo = new CareerInfoTO();
				careerInfo.setEmpCode(rs.getString("emp_code"));
				careerInfo.setCareerCode(rs.getString("career_code"));
				careerInfo.setCompanyName(rs.getString("company_name"));
				careerInfo.setOccupation(rs.getString("occupation"));
				careerInfo.setAssignmentTask(rs.getString("assignment_task"));
				careerInfo.setExHiredate(rs.getString("ex_hiredate"));
				careerInfo.setExRetirementDate(rs.getString("ex_retirement_date"));
				careerlist.add(careerInfo);
			}

			return careerlist;

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertCareerInfo(CareerInfoTO careerInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(
					"INSERT INTO CAREER_INFO VALUES (?, CAREER_CODE_SEQ.NEXTVAL, ?, ?, ?, to_date(?,'YYYY/MM/DD'), to_date(?,'YYYY/MM/DD'))");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getEmpCode());
			pstmt.setString(2, careerInfo.getCompanyName());
			pstmt.setString(3, careerInfo.getOccupation());
			pstmt.setString(4, careerInfo.getAssignmentTask());
			pstmt.setString(5, careerInfo.getExHiredate());
			pstmt.setString(6, careerInfo.getExRetirementDate());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void updateCareerInfo(CareerInfoTO careerInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("UPDATE CAREER_INFO SET ");
			query.append("COMPANY_NAME = ?, OCCUPATION = ?, ASSIGNMENT_TASK = ?, ");
			query.append("EX_HIREDATE = TO_DATE(?,'YYYY/MM/DD'), EX_RETIREMENT_DATE = TO_DATE(?,'YYYY/MM/DD') ");
			query.append("WHERE EMP_CODE = ? and CAREER_CODE = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getCompanyName());
			pstmt.setString(2, careerInfo.getOccupation());
			pstmt.setString(3, careerInfo.getAssignmentTask());
			pstmt.setString(4, careerInfo.getExHiredate());
			pstmt.setString(5, careerInfo.getExRetirementDate());
			pstmt.setString(6, careerInfo.getEmpCode());
			pstmt.setString(7, careerInfo.getCareerCode());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteCareerInfo(CareerInfoTO careerInfo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM CAREER_INFO WHERE EMP_CODE = ? AND CAREER_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getEmpCode());
			pstmt.setString(2, careerInfo.getCareerCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
}
