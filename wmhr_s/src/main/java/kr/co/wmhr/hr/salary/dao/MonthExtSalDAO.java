package kr.co.wmhr.hr.salary.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.salary.to.MonthExtSalTO;

public interface MonthExtSalDAO {
	public ArrayList<MonthExtSalTO> selectMonthExtSalList(String applyYearMonth, String empCode);
}
