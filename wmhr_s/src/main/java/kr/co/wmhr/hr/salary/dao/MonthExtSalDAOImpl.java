package kr.co.wmhr.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.salary.to.MonthExtSalTO;

public class MonthExtSalDAOImpl implements MonthExtSalDAO{
	private DataSourceTransactionManager dataSourceTransactionManager;
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}
	@Override
	public ArrayList<MonthExtSalTO> selectMonthExtSalList(String applyYearMonth, String empCode) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MonthExtSalTO> monthExtSalList=new ArrayList<MonthExtSalTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * ");
			query.append("from month_ext_sal ");
			query.append("where apply_year_month = ? and emp_code = ?  ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, applyYearMonth);
			pstmt.setString(2, empCode);
			rs = pstmt.executeQuery();

			MonthExtSalTO monthExtSal = null;
			while (rs.next()) {
				monthExtSal = new MonthExtSalTO();
				monthExtSal.setEmpCode(rs.getString("EMP_CODE"));
				monthExtSal.setApplyYearMonth(rs.getString("apply_year_month"));
				monthExtSal.setExtSalCode(rs.getString("ext_sal_code"));
				monthExtSal.setExtSalName(rs.getString("ext_sal_name"));
				monthExtSal.setPrice(rs.getString("price"));
				monthExtSalList.add(monthExtSal);
			}
			return monthExtSalList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}

