package kr.co.wmhr.hr.salary.sf;

import java.util.ArrayList;

import kr.co.wmhr.hr.salary.applicationService.SalaryApplicationService;
import kr.co.wmhr.hr.salary.to.BaseDeductionTO;
import kr.co.wmhr.hr.salary.to.BaseExtSalTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;
import kr.co.wmhr.hr.salary.to.MonthSalaryTO;

public class SalaryServiceFacadeImpl implements SalaryServiceFacade{

	private SalaryApplicationService salaryApplicationService;

	public void setSalaryApplicationService(SalaryApplicationService salaryApplicationService) {
		this.salaryApplicationService = salaryApplicationService;
	}

	@Override
	public ArrayList<BaseExtSalTO> findBaseExtSalList() {
			ArrayList<BaseExtSalTO> baseExtSalList=salaryApplicationService.findBaseExtSalList();
			return baseExtSalList;
	}

	@Override
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList) {
		
	}
	@Override
	public MonthSalaryTO findMonthSalary(String ApplyYearMonth, String empCode) {
			MonthSalaryTO monthSalary=salaryApplicationService.findMonthSalary(ApplyYearMonth, empCode);
			return monthSalary;
	}
	@Override
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode) {
			ArrayList<MonthSalaryTO> monthSalary=salaryApplicationService.findYearSalary(applyYear, empCode);
			return monthSalary;
	}
	@Override
	public void modifyMonthSalary(MonthSalaryTO monthSalary) {
			salaryApplicationService.modifyMonthSalary(monthSalary);
	}
	@Override
	public ArrayList<BaseDeductionTO> findBaseDeductionList() {
			ArrayList<BaseDeductionTO> baseDeductionList=salaryApplicationService.findBaseDeductionList();
			return baseDeductionList;
	}
	@Override
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList) {
			salaryApplicationService.batchBaseDeductionProcess(baseDeductionList);
	}
	@Override
	public ArrayList<BaseSalaryTO> findBaseSalaryList() {
			ArrayList<BaseSalaryTO> baseSalaryList=salaryApplicationService.findBaseSalaryList();
			return baseSalaryList;
	}
	@Override
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList) {
			salaryApplicationService.modifyBaseSalaryList(baseSalaryList);
	}

}
