package kr.co.wmhr.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.salary.to.BaseDeductionTO;

public class BaseDeductionDAOImpl implements BaseDeductionDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<BaseDeductionTO> selectBaseDeductionList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from base_deduction ");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseDeductionTO> baseDeductionList = new ArrayList<BaseDeductionTO>();
			BaseDeductionTO baseDeduction = null;
			while (rs.next()) {
				baseDeduction = new BaseDeductionTO();
				baseDeduction.setDeductionCode(rs.getString("deduction_code"));
				baseDeduction.setDeductionName(rs.getString("deduction_name"));
				baseDeduction.setRatio(rs.getString("ratio"));
				baseDeductionList.add(baseDeduction);
			}
			return baseDeductionList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void insertBaseDeduction(BaseDeductionTO baseDeduction) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("insert into base_deduction values (?, ?, ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionCode());
			pstmt.setString(2, baseDeduction.getDeductionName());
			pstmt.setString(3, baseDeduction.getRatio());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void updateBaseDeduction(BaseDeductionTO baseDeduction) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("update base_deduction set ");
			query.append("deduction_name = ?, ratio = ? ");
			query.append("where deduction_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionName());
			pstmt.setString(2, baseDeduction.getRatio());
			pstmt.setString(3, baseDeduction.getDeductionCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteBaseDeduction(BaseDeductionTO baseDeduction) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("delete from base_deduction where deduction_code = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

}
