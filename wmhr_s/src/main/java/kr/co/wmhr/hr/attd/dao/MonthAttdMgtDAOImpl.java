package kr.co.wmhr.hr.attd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.to.ResultTO;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;

public class MonthAttdMgtDAOImpl implements MonthAttdMgtDAO{


	private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	
	@Override
	public HashMap<String, Object> batchMonthAttdMgtProcess(String applyYearMonth) {
	

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ResultTO resultTO = null;
		ArrayList<MonthAttdMgtTO> monthAttdMgtList=new ArrayList<MonthAttdMgtTO>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("{call P_HR_ATTENDANCE.P_CREATE_MONTH_ATTD_MANAGE(?,?,?,?,?)}");
			cstmt = con.prepareCall(query.toString());
			System.out.println(applyYearMonth);
			cstmt.setString(1, applyYearMonth);
			cstmt.setString(2, "인사팀");
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.execute();

			resultTO = new ResultTO();
			resultTO.setErrorCode(cstmt.getString(4));
			resultTO.setErrorMsg(cstmt.getString(5));
			
			rs = (ResultSet)cstmt.getObject(3);
			MonthAttdMgtTO monthAttdMgt = null;
			while (rs.next()) {
				monthAttdMgt = new MonthAttdMgtTO();
				monthAttdMgt.setEmpCode(rs.getString("EMP_CODE"));
				monthAttdMgt.setEmpName(rs.getString("EMP_NAME"));
				monthAttdMgt.setApplyYearMonth(rs.getString("APPLY_YEAR_MONTH"));
				monthAttdMgt.setBasicWorkDays(rs.getString("BASIC_WORK_DAYS"));
				monthAttdMgt.setWeekdayWorkDays(rs.getString("WEEKDAY_WORK_DAYS"));
				monthAttdMgt.setBasicWorkHour(rs.getString("BASIC_WORK_HOUR"));
				monthAttdMgt.setWorkHour(rs.getString("WORK_HOUR"));
				monthAttdMgt.setOverWorkHour(rs.getString("OVER_WORK_HOUR"));
				monthAttdMgt.setNightWorkHour(rs.getString("NIGHT_WORK_HOUR"));
				monthAttdMgt.setHolidayWorkDays(rs.getString("HOLIDAY_WORK_DAYS"));
				monthAttdMgt.setHolidayWorkHour(rs.getString("HOLIDAY_WORK_HOUR"));
				monthAttdMgt.setLateDays(rs.getString("LATE_DAYS"));
				monthAttdMgt.setEarlyLeaveDays(rs.getString("EARLY_LEAVE_DAYS"));
				monthAttdMgt.setAbsentDays(rs.getString("ABSENT_DAYS"));
				monthAttdMgt.setHalfHolidays(rs.getString("HALF_HOLIDAYS"));
				monthAttdMgt.setHolidays(rs.getString("HOLIDAYS"));
				monthAttdMgt.setFinalizeStatus(rs.getString("FINALIZE_STATUS"));
				monthAttdMgtList.add(monthAttdMgt);
			}
			resultMap.put("monthAttdMgtList", monthAttdMgtList);
			resultMap.put("resultTO", resultTO);
	
			return resultMap;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt,rs);
		}
	}

	@Override
	public void updateMonthAttdMgtList(MonthAttdMgtTO monthAttdMgt) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE MONTH_ATTD_MANAGE SET ");
			query.append("FINALIZE_STATUS = ? ");
			query.append("WHERE EMP_CODE = ? AND APPLY_YEAR_MONTH = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, monthAttdMgt.getFinalizeStatus());
			pstmt.setString(2, monthAttdMgt.getEmpCode());
			pstmt.setString(3, monthAttdMgt.getApplyYearMonth());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}


}
