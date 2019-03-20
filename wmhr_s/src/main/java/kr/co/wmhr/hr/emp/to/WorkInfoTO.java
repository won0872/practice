package kr.co.wmhr.hr.emp.to;

import kr.co.wmhr.base.to.BaseTO;

public class WorkInfoTO extends BaseTO{
	private String empCode, workInfoDays, hiredate, retireDate, 
	occupation, employmentType, hobong, position, deptName;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getWorkInfoDays() {
		return workInfoDays;
	}

	public void setWorkInfoDays(String workInfoDays) {
		this.workInfoDays = workInfoDays;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public String getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(String retireDate) {
		this.retireDate = retireDate;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getHobong() {
		return hobong;
	}

	public void setHobong(String hobong) {
		this.hobong = hobong;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
