package kr.co.wmhr.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class BaseSalaryDAOImpl implements BaseSalaryDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<BaseSalaryTO> selectBaseSalaryList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from position ");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseSalaryTO> baseSalaryList = new ArrayList<BaseSalaryTO>();
			BaseSalaryTO baseSalary = null;
			while (rs.next()) {
				baseSalary = new BaseSalaryTO();
				baseSalary.setPositionCode(rs.getString("position_code"));
				baseSalary.setPosition(rs.getString("position"));
				baseSalary.setBaseSalary(rs.getString("base_salary"));
				baseSalary.setHobongRatio(rs.getString("hobong_ratio"));
				baseSalaryList.add(baseSalary);
			}
			return baseSalaryList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateBaseSalary(BaseSalaryTO baseSalary) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("update base_salary set ");
			query.append("base_salary = ?, hobong_ratio = ? ");
			query.append("where position_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseSalary.getBaseSalary());
			pstmt.setString(2, baseSalary.getHobongRatio());
			pstmt.setString(3, baseSalary.getPositionCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void insertPosition(BaseSalaryTO position) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO POSITION VALUES ( ?, ?, ?, ? ) ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, position.getPositionCode());
			pstmt.setString(2, position.getPosition());
			pstmt.setString(3, position.getBaseSalary());
			pstmt.setString(4, position.getHobongRatio());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void updatePosition(BaseSalaryTO position) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(" UPDATE  POSITION SET ");
			query.append(" POSITION = ?, BASE_SALARY = ?, HOBONG_RATIO = ? ");
			query.append(" WHERE POSITION_CODE = ? ");

			pstmt = con.prepareStatement(query.toString());

			pstmt.setString(1, position.getPosition());
			pstmt.setString(2, position.getBaseSalary());
			pstmt.setString(3, position.getHobongRatio());
			pstmt.setString(4, position.getPositionCode());
			pstmt.executeUpdate();
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deletePosition(BaseSalaryTO position) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(" DELETE  POSITION ");
			query.append(" WHERE POSITION_CODE = ? AND POSITION = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, position.getPositionCode());
			pstmt.setString(2, position.getPosition());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
}
