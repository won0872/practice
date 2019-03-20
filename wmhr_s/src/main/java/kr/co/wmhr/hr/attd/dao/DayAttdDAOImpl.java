package kr.co.wmhr.hr.attd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.attd.to.DayAttdTO;

public class DayAttdDAOImpl implements DayAttdDAO{

	private DataSourceTransactionManager dataSourceTransactionManager;
	
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<DayAttdTO> selectDayAttdList(String empCode, String applyDay) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DayAttdTO> dayAttdList=new ArrayList<DayAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("SELECT D.EMP_CODE, E.EMP_NAME, D.DAY_ATTD_CODE, ");
			query.append("TO_CHAR(D.APPLY_DAY, 'YYYY/MM/DD') APPLY_DAY, ");
			query.append("D.ATTD_TYPE_CODE, D.ATTD_TYPE_NAME, D.TIME ");
			query.append("FROM DAY_ATTD D, EMP E WHERE D.EMP_CODE = ? AND D.APPLY_DAY = ? ");
			query.append("AND D.EMP_CODE = E.EMP_CODE ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, applyDay);
			rs = pstmt.executeQuery();

			DayAttdTO dayAttd = null;
			while (rs.next()) {
				dayAttd = new DayAttdTO();
				dayAttd.setEmpCode(rs.getString("EMP_CODE"));
				dayAttd.setEmpName(rs.getString("EMP_NAME"));
				dayAttd.setDayAttdCode(rs.getString("DAY_ATTD_CODE"));
				dayAttd.setApplyDay(rs.getString("APPLY_DAY"));
				dayAttd.setAttdTypeCode(rs.getString("ATTD_TYPE_CODE"));
				dayAttd.setAttdTypeName(rs.getString("ATTD_TYPE_NAME"));
				dayAttd.setTime(rs.getString("TIME"));
				dayAttdList.add(dayAttd);
			}
			
			return dayAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void insertDayAttd(DayAttdTO dayAttd) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO DAY_ATTD VALUES (?, DAY_ATTD_CODE_SEQ.NEXTVAL, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dayAttd.getEmpCode());
			pstmt.setString(2, dayAttd.getApplyDay());
			pstmt.setString(3, dayAttd.getAttdTypeCode());
			pstmt.setString(4, dayAttd.getAttdTypeName());
			pstmt.setString(5, dayAttd.getTime());
			pstmt.executeUpdate();
			
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void deleteDayAttd(DayAttdTO dayAttd) {
	

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM DAY_ATTD WHERE EMP_CODE = ? AND DAY_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dayAttd.getEmpCode());
			pstmt.setString(2, dayAttd.getDayAttdCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

}
