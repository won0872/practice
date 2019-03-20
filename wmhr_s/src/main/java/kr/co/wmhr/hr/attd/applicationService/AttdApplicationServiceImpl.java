package kr.co.wmhr.hr.attd.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.to.ResultTO;
import kr.co.wmhr.hr.attd.dao.DayAttdDAO;
import kr.co.wmhr.hr.attd.dao.DayAttdMgtDAO;
import kr.co.wmhr.hr.attd.dao.MonthAttdMgtDAO;
import kr.co.wmhr.hr.attd.dao.RestAttdDAO;
import kr.co.wmhr.hr.attd.to.DayAttdMgtTO;
import kr.co.wmhr.hr.attd.to.DayAttdTO;
import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;
import kr.co.wmhr.hr.attd.to.RestAttdTO;

public class AttdApplicationServiceImpl implements AttdApplicationService{

	private DayAttdDAO dayAttdDAO;
	private RestAttdDAO restAttdDAO;
	private DayAttdMgtDAO dayAttdMgtDAO;
	private MonthAttdMgtDAO monthAttdMgtDAO;
	
	public void setDayAttdDAO(DayAttdDAO dayAttdDAO) {
		this.dayAttdDAO = dayAttdDAO;
	}

	public void setRestAttdDAO(RestAttdDAO restAttdDAO) {
		this.restAttdDAO = restAttdDAO;
	}

	public void setDayAttdMgtDAO(DayAttdMgtDAO dayAttdMgtDAO) {
		this.dayAttdMgtDAO = dayAttdMgtDAO;
	}

	public void setMonthAttdMgtDAO(MonthAttdMgtDAO monthAttdMgtDAO) {
		this.monthAttdMgtDAO = monthAttdMgtDAO;
	}

	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		

		ArrayList<DayAttdTO> dayAttdList=dayAttdDAO.selectDayAttdList(empCode, applyDay);

		
		return dayAttdList;
	}

	@Override
	public void registDayAttd(DayAttdTO dayAttd) {
		
		dayAttdDAO.insertDayAttd(dayAttd);
		
		
	}

	@Override
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList) {
		
		for(DayAttdTO dayAttd : dayAttdList){
			dayAttdDAO.deleteDayAttd(dayAttd);
		}
		
		
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
		
		ArrayList<RestAttdTO> restAttdList=null;
	
		if(code == "")
			restAttdList = restAttdDAO.selectRestAttdList(empCode, startDate, endDate);
		else
			restAttdList = restAttdDAO.selectRestAttdListCode(empCode, startDate, endDate, code);
	
		return restAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findRejectRestAttdList(String empCode, String applyYear) {

		ArrayList<RestAttdTO> rejectRestAttdList = restAttdDAO.selectRejectRestAttdList(empCode, applyYear);
		
		return rejectRestAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept(String deptName, String startDate, String endDate) {
		ArrayList<RestAttdTO> restAttdList = null;
		
		if(deptName.equals("모든부서")) {
			restAttdList = restAttdDAO.selectRestAttdListByAllDept(startDate);
		}else {
			restAttdList = restAttdDAO.selectRestAttdListByDept(deptName, startDate, endDate);
		}
		
		
		return restAttdList;
	}
	@Override
	public RestAttdTO findRestAttdListByToday(String empCode, String toDay) {
		
		RestAttdTO restAttdList = restAttdDAO.selectRestAttdListByToday(empCode, toDay);
		
		return restAttdList;
	}
	@Override
	public ArrayList<RestAttdTO> findUseAnnualLeaveList(String empCode, String startDate, String endDate) {
		
		ArrayList<RestAttdTO> useAnnualLeaveList = restAttdDAO.selectUseAnnualLeaveList(empCode,startDate, endDate);
		
		return useAnnualLeaveList;
	}
	@Override
	public void registRestAttd(RestAttdTO restAttd) {
		
		restAttdDAO.insertRestAttd(restAttd);
	
		
	}
	@Override
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		
		for(RestAttdTO restAttd : restAttdList){
			if(restAttd.getStatus().equals("update")){
				restAttdDAO.updateRestAttd(restAttd);
			}
		}
		
		
	}
	@Override
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		
		for(RestAttdTO restAttd : restAttdList){
			restAttdDAO.deleteRestAttd(restAttd);
		}
		
	}
	@Override
	public void removeRejectRestAttdList(ArrayList<RestAttdTO> rejectRestAttdList) {
		
		for(RestAttdTO rejectRestAttd : rejectRestAttdList){
			restAttdDAO.deleteRestAttd(rejectRestAttd);
		}
	
		
	}
	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String dept) {
		

		HashMap<String, Object> resultMap = dayAttdMgtDAO.batchDayAttdMgtProcess(applyDay, dept);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<DayAttdMgtTO> dayAttdMgtList = (ArrayList<DayAttdMgtTO>) resultMap.get("dayAttdMgtList");

		
		return dayAttdMgtList;
	}
	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {
	

		for(DayAttdMgtTO dayAttdMgt : dayAttdMgtList){
			if(dayAttdMgt.getStatus().equals("update")){
				dayAttdMgtDAO.updateDayAttdMgtList(dayAttdMgt);
			}
		}
		
	}
	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth) {
		

		HashMap<String, Object> resultMap = monthAttdMgtDAO.batchMonthAttdMgtProcess(applyYearMonth);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<MonthAttdMgtTO> monthAttdMgtList = (ArrayList<MonthAttdMgtTO>) resultMap.get("monthAttdMgtList");
		
		
		return monthAttdMgtList;
	}
	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
		

		for(MonthAttdMgtTO monthAttdMgt : monthAttdMgtList){
			if(monthAttdMgt.getStatus().equals("update")){
				monthAttdMgtDAO.updateMonthAttdMgtList(monthAttdMgt);
			}
		}

		
		
	}

}
