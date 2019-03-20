package kr.co.wmhr.hr.attd.to;

import kr.co.wmhr.base.to.BaseTO;

public class MonthAttdMgtTO extends BaseTO{
	private String empCode, empName, applyYearMonth ,basicWorkDays
	,weekdayWorkDays ,basicWorkHour ,workHour
	,overWorkHour ,nightWorkHour ,holidayWorkDays,earlyLeaveDays
	,holidayWorkHour ,lateDays ,absentDays ,halfHolidays ,Holidays ,finalizeStatus;

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

	public String getApplyYearMonth() {
		return applyYearMonth;
	}

	public void setApplyYearMonth(String applyYearMonth) {
		this.applyYearMonth = applyYearMonth;
	}

	public String getBasicWorkDays() {
		return basicWorkDays;
	}

	public void setBasicWorkDays(String basicWorkDays) {
		this.basicWorkDays = basicWorkDays;
	}

	public String getWeekdayWorkDays() {
		return weekdayWorkDays;
	}

	public void setWeekdayWorkDays(String weekdayWorkDays) {
		this.weekdayWorkDays = weekdayWorkDays;
	}

	public String getBasicWorkHour() {
		return basicWorkHour;
	}

	public void setBasicWorkHour(String basicWorkHour) {
		this.basicWorkHour = basicWorkHour;
	}

	public String getWorkHour() {
		return workHour;
	}

	public void setWorkHour(String workHour) {
		this.workHour = workHour;
	}

	public String getOverWorkHour() {
		return overWorkHour;
	}

	public void setOverWorkHour(String overWorkHour) {
		this.overWorkHour = overWorkHour;
	}

	public String getNightWorkHour() {
		return nightWorkHour;
	}

	public void setNightWorkHour(String nightWorkHour) {
		this.nightWorkHour = nightWorkHour;
	}

	public String getHolidayWorkDays() {
		return holidayWorkDays;
	}

	public void setHolidayWorkDays(String holidayWorkDays) {
		this.holidayWorkDays = holidayWorkDays;
	}

	public String getEarlyLeaveDays() {
		return earlyLeaveDays;
	}

	public void setEarlyLeaveDays(String earlyLeaveDays) {
		this.earlyLeaveDays = earlyLeaveDays;
	}

	public String getHolidayWorkHour() {
		return holidayWorkHour;
	}

	public void setHolidayWorkHour(String holidayWorkHour) {
		this.holidayWorkHour = holidayWorkHour;
	}

	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}

	public String getAbsentDays() {
		return absentDays;
	}

	public void setAbsentDays(String absentDays) {
		this.absentDays = absentDays;
	}

	public String getHalfHolidays() {
		return halfHolidays;
	}

	public void setHalfHolidays(String halfHolidays) {
		this.halfHolidays = halfHolidays;
	}

	public String getHolidays() {
		return Holidays;
	}

	public void setHolidays(String holidays) {
		Holidays = holidays;
	}

	public String getFinalizeStatus() {
		return finalizeStatus;
	}

	public void setFinalizeStatus(String finalizeStatus) {
		this.finalizeStatus = finalizeStatus;
	}
}