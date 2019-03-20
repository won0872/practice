package kr.co.wmhr.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.salary.to.MonthDeductionTO;

public class MonthDeductionDAOImpl implements MonthDeductionDAO{
	private DataSourceTransactionManager dataSourceTransactionManager;
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}
	@Override
	public ArrayList<MonthDeductionTO> selectMonthDeductionList(String applyYearMonth, String empCode) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MonthDeductionTO> monthDeductionList=new ArrayList<MonthDeductionTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * ");
			query.append("from month_deduction ");
			query.append("where apply_year_month = ? and emp_code = ?  ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, applyYearMonth);
			pstmt.setString(2, empCode);
			rs = pstmt.executeQuery();

			MonthDeductionTO monthDeduction = null;
			while (rs.next()) {
				monthDeduction = new MonthDeductionTO();
				monthDeduction.setEmpCode(rs.getString("EMP_CODE"));
				monthDeduction.setApplyYearMonth(rs.getString("apply_year_month"));
				monthDeduction.setDeductionCode(rs.getString("deduction_code"));
				monthDeduction.setDeductionName(rs.getString("deduction_name"));
				monthDeduction.setPrice(rs.getString("price"));
				monthDeductionList.add(monthDeduction);
			}
			return monthDeductionList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}
