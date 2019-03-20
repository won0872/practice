package kr.co.wmhr.hr.attd.to;

import kr.co.wmhr.base.to.BaseTO;

public class RestAttdTO extends BaseTO{
	private String empCode, empName, restAttdCode, restTypeCode
	,restTypeName, requestDate, startDate
	,endDate, numberOfDays, cost, cause
	,applovalStatus, rejectCause, startTime, endTime;

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

	public String getRestAttdCode() {
		return restAttdCode;
	}

	public void setRestAttdCode(String restAttdCode) {
		this.restAttdCode = restAttdCode;
	}

	public String getRestTypeCode() {
		return restTypeCode;
	}

	public void setRestTypeCode(String restTypeCode) {
		this.restTypeCode = restTypeCode;
	}

	public String getRestTypeName() {
		return restTypeName;
	}

	public void setRestTypeName(String restTypeName) {
		this.restTypeName = restTypeName;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getApplovalStatus() {
		return applovalStatus;
	}

	public void setApplovalStatus(String applovalStatus) {
		this.applovalStatus = applovalStatus;
	}

	public String getRejectCause() {
		return rejectCause;
	}

	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
