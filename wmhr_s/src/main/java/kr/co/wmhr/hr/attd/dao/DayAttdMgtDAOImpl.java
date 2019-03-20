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
import kr.co.wmhr.hr.attd.to.DayAttdMgtTO;

public class DayAttdMgtDAOImpl implements DayAttdMgtDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public HashMap<String, Object> batchDayAttdMgtProcess(String applyDay, String dept) {

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ResultTO resultTO = null;
		ArrayList<DayAttdMgtTO> dayAttdMgtList = new ArrayList<DayAttdMgtTO>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("{call P_HR_ATTENDANCE.P_CREATE_DAY_ATTD_MANAGE(TO_DATE(?,'YYYY-MM-DD'),?,?,?,?)}");
			cstmt = con.prepareCall(query.toString());
			cstmt.setString(1, applyDay);
			cstmt.setString(2, dept);
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.execute();

			resultTO = new ResultTO();
			resultTO.setErrorCode(cstmt.getString(4));
			resultTO.setErrorMsg(cstmt.getString(5));

			rs = (ResultSet) cstmt.getObject(3);
			DayAttdMgtTO datAttdMgt = null;
			while (rs.next()) {
				datAttdMgt = new DayAttdMgtTO();
				datAttdMgt.setEmpCode(rs.getString("EMP_CODE"));
				datAttdMgt.setEmpName(rs.getString("EMP_NAME"));
				datAttdMgt.setApplyDays(rs.getString("APPLY_DAYS"));
				datAttdMgt.setDayAttdCode(rs.getString("DAY_ATTD_CODE"));
				datAttdMgt.setDayAttdName(rs.getString("DAY_ATTD_NAME"));
				datAttdMgt.setAttendTime(rs.getString("ATTEND_TIME"));
				datAttdMgt.setQuitTime(rs.getString("QUIT_TIME"));
				datAttdMgt.setLateWhether(rs.getString("LATE_WHETHER"));
				datAttdMgt.setLeaveHour(rs.getString("LEAVE_HOUR"));
				datAttdMgt.setWorkHour(rs.getString("WORK_HOUR"));
				datAttdMgt.setOverWorkHour(rs.getString("OVER_WORK_HOUR"));
				datAttdMgt.setNightWorkHour(rs.getString("NIGHT_WORK_HOUR"));
				datAttdMgt.setFinalizeStatus(rs.getString("FINALIZE_STATUS"));
				dayAttdMgtList.add(datAttdMgt);
			}
			resultMap.put("dayAttdMgtList", dayAttdMgtList);
			resultMap.put("resultTO", resultTO);

			return resultMap;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt, rs);
		}
	}

	@Override
	public void updateDayAttdMgtList(DayAttdMgtTO dayAttdMgt) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE DAY_ATTD_MANAGE SET ");
			query.append("FINALIZE_STATUS = ? ");
			query.append("WHERE EMP_CODE = ? AND APPLY_DAYS = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dayAttdMgt.getFinalizeStatus());
			pstmt.setString(2, dayAttdMgt.getEmpCode());
			pstmt.setString(3, dayAttdMgt.getApplyDays());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

}