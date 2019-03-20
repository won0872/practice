package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.HolidayTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class HolidayDAOImpl implements HolidayDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public void insertCodeList(HolidayTO holyday) {

		Connection con = null;

		PreparedStatement pstmt = null;

		try {

			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append(" insert into holiday values(TO_DATE(?,'YYYY-MM-DD') ,?,?)");

			pstmt = con.prepareStatement(query.toString());

			pstmt.setString(1, holyday.getApplyDay());

			pstmt.setString(2, holyday.getHolidayName());

			pstmt.setString(3, holyday.getNote());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public ArrayList<HolidayTO> selectHolidayList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select to_char(apply_day, 'YYYY-MM-DD') apply_day, holiday_name, note from holiday ");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<HolidayTO> holidayList = new ArrayList<HolidayTO>();
			HolidayTO holiday = null;
			while (rs.next()) {
				holiday = new HolidayTO();
				holiday.setApplyDay(rs.getString("APPLY_DAY"));
				holiday.setHolidayName(rs.getString("HOLIDAY_NAME"));
				holiday.setNote(rs.getString("NOTE"));
				holidayList.add(holiday);
			}

			return holidayList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public String selectWeekDayCount(String startDate, String endDate) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT WEEKDAY_COUNTING_FUNC(?,?) WEEKDAY_COUNT FROM DUAL ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			rs = pstmt.executeQuery();

			String weekdayCount = null;
			while (rs.next()) {
				weekdayCount = rs.getString("WEEKDAY_COUNT");
			}

			return weekdayCount;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}