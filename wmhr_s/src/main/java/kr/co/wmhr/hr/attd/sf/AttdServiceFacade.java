package kr.co.wmhr.hr.attd.sf;

import java.util.ArrayList;

import kr.co.wmhr.hr.attd.to.DayAttdMgtTO;
import kr.co.wmhr.hr.attd.to.DayAttdTO;
import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;
import kr.co.wmhr.hr.attd.to.RestAttdTO;

public interface AttdServiceFacade {
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay);
	public void registDayAttd(DayAttdTO dayAttd);
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList);

	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code);
	public ArrayList<RestAttdTO> findRejectRestAttdList(String empCode, String applyYear);
	public ArrayList<RestAttdTO> findRestAttdListByDept(String deptName, String startDate, String endDate);
	public RestAttdTO findRestAttdListByToday(String empCode, String toDay);
	public ArrayList<RestAttdTO> findUseAnnualLeaveList(String empCode, String startDate, String endDate);
	public void registRestAttd(RestAttdTO restAttd);
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList);
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList);
	public void removeRejectRestAttdList(ArrayList<RestAttdTO> rejectRestAttdList);

	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String dept);
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList);

	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth);
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList);
	
	/*public AnnualLeaveTO findAnnualLeave(String applyYear,String applyDay, String empCode);*/
}