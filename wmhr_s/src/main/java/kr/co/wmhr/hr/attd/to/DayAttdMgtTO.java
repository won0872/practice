package kr.co.wmhr.hr.attd.to;

import kr.co.wmhr.base.to.BaseTO;

public class DayAttdMgtTO extends BaseTO{
	private String empCode, empName, applyDays ,dayAttdCode
	,dayAttdName ,attendTime,HalfHolidayStatus
	,quitTime ,lateWhether ,leaveHour ,workHour,earlyLeaveHour
	,overWorkHour ,nightWorkHour ,finalizeStatus;

	public String getEarlyLeaveHour() {
		return earlyLeaveHour;
	}

	public void setEarlyLeaveHour(String earlyLeaveHour) {
		this.earlyLeaveHour = earlyLeaveHour;
	}

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


	public String getApplyDays() {
		return applyDays;
	}

	public void setApplyDays(String applyDays) {
		this.applyDays = applyDays;
	}

	public String getHalfHolidayStatus() {
		return HalfHolidayStatus;
	}

	public void setHalfHolidayStatus(String halfHolidayStatus) {
		HalfHolidayStatus = halfHolidayStatus;
	}

	public String getLateWhether() {
		return lateWhether;
	}

	public void setLateWhether(String lateWhether) {
		this.lateWhether = lateWhether;
	}

	public String getDayAttdCode() {
		return dayAttdCode;
	}

	public void setDayAttdCode(String dayAttdCode) {
		this.dayAttdCode = dayAttdCode;
	}

	public String getDayAttdName() {
		return dayAttdName;
	}

	public void setDayAttdName(String dayAttdName) {
		this.dayAttdName = dayAttdName;
	}

	public String getAttendTime() {
		return attendTime;
	}

	public void setAttendTime(String attendTime) {
		this.attendTime = attendTime;
	}

	public String getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}



	public String getLeaveHour() {
		return leaveHour;
	}

	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
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

	public String getFinalizeStatus() {
		return finalizeStatus;
	}

	public void setFinalizeStatus(String finalizeStatus) {
		this.finalizeStatus = finalizeStatus;
	}
}
