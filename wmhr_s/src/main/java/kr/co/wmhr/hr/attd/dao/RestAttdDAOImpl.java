package kr.co.wmhr.hr.attd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.attd.to.RestAttdTO;

public class RestAttdDAOImpl implements RestAttdDAO{
	private DataSourceTransactionManager dataSourceTransactionManager;

	

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<RestAttdTO> selectRestAttdList(String empCode, String startDate, String endDate) {
		

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
		
			return restAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<RestAttdTO> selectRejectRestAttdList(String empCode, String applyYear) {


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? ");
			query.append("AND R.REQUEST_DATE BETWEEN TO_DATE((?-1)||'-01-01','RRRR-MM-DD') AND TO_DATE((?+1)||'-01-01','RRRR-MM-DD')  ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");
			query.append("AND APPLOVAL_STATUS IN ('반려','승인취소','취소') ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, applyYear);
			pstmt.setString(3, applyYear);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}

			return restAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<RestAttdTO> selectRestAttdListByDept(String deptName, String startDate, String endDate) {


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE E.DEPT_NAME = ? AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, deptName);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
	
			return restAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<RestAttdTO> selectRestAttdListByAllDept(String applyDay) {


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.REQUEST_DATE = TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, applyDay);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}

			return restAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public RestAttdTO selectRestAttdListByToday(String empCode, String toDay) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME,R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? AND R.START_DATE = to_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.END_DATE = to_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.APPLOVAL_STATUS = '승인' ");
			query.append("AND R.REST_TYPE_CODE <> 'ASC008' ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, toDay);
			pstmt.setString(3, toDay);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
			}
	
			return restAttd;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<RestAttdTO> selectUseAnnualLeaveList(String empCode, String startDate, String endDate) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> useAnnualLeaveList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM REST_ATTD ");
			query.append("WHERE EMP_CODE = ? ");
			query.append("AND START_DATE BETWEEN TO_DATE(?||'-01-01','RRRR-MM-DD') ");
			query.append("AND TO_DATE(?||'-12-31','RRRR-MM-DD') ");
			query.append("AND REST_TYPE_CODE IN ('ASC005','ASC006','ASC007') ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				useAnnualLeaveList.add(restAttd);
			}

			return useAnnualLeaveList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void insertRestAttd(RestAttdTO restAttd) {


		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO REST_ATTD VALUES (?,REST_ATTD_CODE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getEmpCode());
			pstmt.setString(2, restAttd.getRestTypeCode());
			pstmt.setString(3, restAttd.getRestTypeName());
			pstmt.setString(4, restAttd.getRequestDate());
			pstmt.setString(5, restAttd.getStartDate());
			pstmt.setString(6, restAttd.getEndDate());
			pstmt.setString(7, restAttd.getCause());
			pstmt.setString(8, restAttd.getApplovalStatus());
			pstmt.setString(9, restAttd.getRejectCause());
			pstmt.setString(10, restAttd.getCost());
			pstmt.setString(11, restAttd.getStartTime());
			pstmt.setString(12, restAttd.getEndTime());
			pstmt.setString(13, restAttd.getNumberOfDays());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void updateRestAttd(RestAttdTO restAttd) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE REST_ATTD SET ");
			query.append("CAUSE = ?, APPLOVAL_STATUS = ?, REJECT_CAUSE = ? ");
			query.append("WHERE EMP_CODE = ? AND REST_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getCause());
			pstmt.setString(2, restAttd.getApplovalStatus());
			pstmt.setString(3, restAttd.getRejectCause());
			pstmt.setString(4, restAttd.getEmpCode());
			pstmt.setString(5, restAttd.getRestAttdCode());
			pstmt.executeUpdate();
	
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void deleteRestAttd(RestAttdTO restAttd) {


		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM REST_ATTD WHERE EMP_CODE = ? AND REST_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getEmpCode());
			pstmt.setString(2, restAttd.getRestAttdCode());
			pstmt.executeUpdate();
	
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	@Override
	public ArrayList<RestAttdTO> selectRestAttdListCode(String empCode, String startDate, String endDate, String code) {
	

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.REST_TYPE_CODE = ? AND R.EMP_CODE = E.EMP_CODE ");

			System.out.println(empCode);
			System.out.println(startDate);
			System.out.println(endDate);
			System.out.println(code);
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			pstmt.setString(4, code);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}

			return restAttdList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}