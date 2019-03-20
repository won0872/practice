package kr.co.wmhr.hr.salary.dao;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.wmhr.hr.salary.to.MonthSalaryTO;

public interface MonthSalaryDAO {
	public MonthSalaryTO selectMonthSalary(String applyYearMonth, String empCode);
	public ArrayList<MonthSalaryTO> selectYearSalary(String applyYear, String empCode);
	public HashMap<String, Object> batchMonthSalaryProcess(String applyYearMonth, String empCode);
	public void updateMonthSalary(MonthSalaryTO monthSalary);
}