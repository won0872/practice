package kr.co.wmhr.hr.attd.dao;

import java.util.HashMap;

import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;

public interface MonthAttdMgtDAO {
	public HashMap<String, Object> batchMonthAttdMgtProcess(String applyYearMonth);
	public void updateMonthAttdMgtList(MonthAttdMgtTO monthAttdMgt);
}
