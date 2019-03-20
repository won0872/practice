package kr.co.wmhr.hr.emp.to;

import kr.co.wmhr.base.to.BaseTO;

public class CareerInfoTO extends BaseTO{
	private String empCode, careerCode, companyName, occupation, assignmentTask, exHiredate, exRetirementDate;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getCareerCode() {
		return careerCode;
	}

	public void setCareerCode(String careerCode) {
		this.careerCode = careerCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAssignmentTask() {
		return assignmentTask;
	}

	public void setAssignmentTask(String assignmentTask) {
		this.assignmentTask = assignmentTask;
	}

	public String getExHiredate() {
		return exHiredate;
	}

	public void setExHiredate(String exHiredate) {
		this.exHiredate = exHiredate;
	}

	public String getExRetirementDate() {
		return exRetirementDate;
	}

	public void setExRetirementDate(String exRetirementDate) {
		this.exRetirementDate = exRetirementDate;
	}
}
