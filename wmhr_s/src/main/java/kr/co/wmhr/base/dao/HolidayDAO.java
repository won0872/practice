package kr.co.wmhr.base.dao;

import java.util.ArrayList;

import kr.co.wmhr.base.to.HolidayTO;

public interface HolidayDAO {
	public ArrayList<HolidayTO> selectHolidayList();
	public void insertCodeList(HolidayTO holyday);
	String selectWeekDayCount(String startDate, String endDate);
}

