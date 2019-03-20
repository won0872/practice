package kr.co.wmhr.hr.salary.sf;

import java.util.ArrayList;

import kr.co.wmhr.hr.salary.to.BaseDeductionTO;
import kr.co.wmhr.hr.salary.to.BaseExtSalTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;
import kr.co.wmhr.hr.salary.to.MonthSalaryTO;

public interface SalaryServiceFacade {
	public MonthSalaryTO findMonthSalary(String ApplyYearMonth, String empCode);
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode);
	public void modifyMonthSalary(MonthSalaryTO monthSalary);

	public ArrayList<BaseDeductionTO> findBaseDeductionList();
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList);

	public ArrayList<BaseSalaryTO> findBaseSalaryList();
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList);

	public ArrayList<BaseExtSalTO> findBaseExtSalList();
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList);
}
