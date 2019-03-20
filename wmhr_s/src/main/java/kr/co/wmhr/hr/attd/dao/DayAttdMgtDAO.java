package kr.co.wmhr.hr.attd.dao;

import java.util.HashMap;

import kr.co.wmhr.hr.attd.to.DayAttdMgtTO;

public interface DayAttdMgtDAO {
	public HashMap<String, Object> batchDayAttdMgtProcess(String applyDay , String dept);
	public void updateDayAttdMgtList(DayAttdMgtTO dayAttdMgt);
}
