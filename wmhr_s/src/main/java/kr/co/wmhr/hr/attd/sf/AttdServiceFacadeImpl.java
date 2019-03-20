package kr.co.wmhr.hr.attd.sf;

import java.util.ArrayList;

import kr.co.wmhr.hr.attd.applicationService.AttdApplicationService;
import kr.co.wmhr.hr.attd.to.DayAttdMgtTO;
import kr.co.wmhr.hr.attd.to.DayAttdTO;
import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;
import kr.co.wmhr.hr.attd.to.RestAttdTO;

public class AttdServiceFacadeImpl implements AttdServiceFacade{
	private AttdApplicationService attdApplicationService;
	public void setAttdApplicationService(AttdApplicationService attdApplicationService) {
		this.attdApplicationService = attdApplicationService;
	}
	

	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
			ArrayList<DayAttdTO> dayAttdList=attdApplicationService.findDayAttdList(empCode, applyDay);
			return dayAttdList;
	}
	@Override
	public void registDayAttd(DayAttdTO dayAttd) {
			attdApplicationService.registDayAttd(dayAttd);
	}
	@Override
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList) {
			attdApplicationService.removeDayAttdList(dayAttdList);
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {

			ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdList(empCode, startDate, endDate, code);
		
			return restAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findRejectRestAttdList(String empCode, String applyYear) {
			ArrayList<RestAttdTO> rejectRestAttdList = attdApplicationService.findRejectRestAttdList(empCode, applyYear);
		
			return rejectRestAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept(String deptName, String startDate, String endDate) {
	
			ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdListByDept(deptName, startDate, endDate);
	
			return restAttdList;
	}
	@Override
	public RestAttdTO findRestAttdListByToday(String empCode, String toDay) {
			RestAttdTO restAttdList = attdApplicationService.findRestAttdListByToday(empCode,toDay);

			return restAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findUseAnnualLeaveList(String empCode, String startDate, String endDate) {

			ArrayList<RestAttdTO> useAnnualLeaveList = attdApplicationService.findUseAnnualLeaveList(empCode,startDate, endDate);
	
			return useAnnualLeaveList;
	}
	@Override
	public void registRestAttd(RestAttdTO restAttd) {
			attdApplicationService.registRestAttd(restAttd);
	}
	@Override
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList) {
			attdApplicationService.modifyRestAttdList(restAttdList);
	}
	@Override
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList) {
			attdApplicationService.removeRestAttdList(restAttdList);		
	}
	@Override
	public void removeRejectRestAttdList(ArrayList<RestAttdTO> rejectRestAttdList) {
			attdApplicationService.removeRejectRestAttdList(rejectRestAttdList);
	}
	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay,String dept) {
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdApplicationService.findDayAttdMgtList(applyDay, dept);
		
			return dayAttdMgtList;
	}
	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {
			attdApplicationService.modifyDayAttdMgtList(dayAttdMgtList);
	}
	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth) {
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = attdApplicationService.findMonthAttdMgtList(applyYearMonth);
			
			return monthAttdMgtList;
	}
	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
			attdApplicationService.modifyMonthAttdMgtList(monthAttdMgtList);		
		
	}
	

}

