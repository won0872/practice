package kr.co.wmhr.hr.attd.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.attd.to.DayAttdTO;

public interface DayAttdDAO {
	public ArrayList<DayAttdTO> selectDayAttdList(String empCode, String applyDay);

	public void insertDayAttd(DayAttdTO dayAttd);
	public void deleteDayAttd(DayAttdTO dayAttd);
}