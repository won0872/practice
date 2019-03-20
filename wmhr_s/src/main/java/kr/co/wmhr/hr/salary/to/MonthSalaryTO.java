package kr.co.wmhr.hr.salary.to;

import java.util.ArrayList;

public class MonthSalaryTO {
	private String empCode,applyYearMonth,salary,totalExtSal,totalPayment,totalDeduction,realSalary,cost,unusedDaySalary,finalizeStatus;
	private ArrayList<MonthDeductionTO> monthDeductionList;
	private ArrayList<MonthExtSalTO> monthExtSalList;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getApplyYearMonth() {
		return applyYearMonth;
	}
	public void setApplyYearMonth(String applyYearMonth) {
		this.applyYearMonth = applyYearMonth;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getTotalExtSal() {
		return totalExtSal;
	}
	public void setTotalExtSal(String totalExtSal) {
		this.totalExtSal = totalExtSal;
	}
	public String getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}
	public String getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public String getRealSalary() {
		return realSalary;
	}
	public void setRealSalary(String realSalary) {
		this.realSalary = realSalary;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getUnusedDaySalary() {
		return unusedDaySalary;
	}
	public void setUnusedDaySalary(String unusedDaySalary) {
		this.unusedDaySalary = unusedDaySalary;
	}
	public String getFinalizeStatus() {
		return finalizeStatus;
	}
	public void setFinalizeStatus(String finalizeStatus) {
		this.finalizeStatus = finalizeStatus;
	}
	public ArrayList<MonthDeductionTO> getMonthDeductionList() {
		return monthDeductionList;
	}
	public void setMonthDeductionList(ArrayList<MonthDeductionTO> monthDeductionList) {
		this.monthDeductionList = monthDeductionList;
	}
	public ArrayList<MonthExtSalTO> getMonthExtSalList() {
		return monthExtSalList;
	}
	public void setMonthExtSalList(ArrayList<MonthExtSalTO> monthExtSalList) {
		this.monthExtSalList = monthExtSalList;
	}
}
