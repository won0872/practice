package kr.co.wmhr.hr.attd.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.attd.to.RestAttdTO;

public interface RestAttdDAO {
	public ArrayList<RestAttdTO> selectRestAttdList(String empCode, String startDate, String endDate);
	public ArrayList<RestAttdTO> selectRestAttdListCode(String empCode, String startDate, String endDate, String code);
	public ArrayList<RestAttdTO> selectRejectRestAttdList(String empCode, String applyYear);
	public ArrayList<RestAttdTO> selectRestAttdListByDept(String deptName, String startDate, String endDate);
	public ArrayList<RestAttdTO> selectRestAttdListByAllDept(String applyDay);
	public RestAttdTO selectRestAttdListByToday(String empCode, String toDay);
	public ArrayList<RestAttdTO> selectUseAnnualLeaveList(String empCode,String startDate,String endDate);
	public void insertRestAttd(RestAttdTO restAttd);
	public void updateRestAttd(RestAttdTO restAttd);
	public void deleteRestAttd(RestAttdTO restAttd);
}