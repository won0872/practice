package kr.co.wmhr.hr.attd.to;

import kr.co.wmhr.base.to.BaseTO;

public class DayAttdTO extends BaseTO{
	private String empCode, empName, dayAttdCode, applyDay
	,attdTypeCode ,attdTypeName, time;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDayAttdCode() {
		return dayAttdCode;
	}

	public void setDayAttdCode(String dayAttdCode) {
		this.dayAttdCode = dayAttdCode;
	}

	public String getApplyDay() {
		return applyDay;
	}

	public void setApplyDay(String applyDay) {
		this.applyDay = applyDay;
	}

	public String getAttdTypeCode() {
		return attdTypeCode;
	}

	public void setAttdTypeCode(String attdTypeCode) {
		this.attdTypeCode = attdTypeCode;
	}

	public String getAttdTypeName() {
		return attdTypeName;
	}

	public void setAttdTypeName(String attdTypeName) {
		this.attdTypeName = attdTypeName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
