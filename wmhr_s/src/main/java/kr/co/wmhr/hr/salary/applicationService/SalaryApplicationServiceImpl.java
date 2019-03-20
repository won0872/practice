package kr.co.wmhr.hr.salary.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.to.ResultTO;
import kr.co.wmhr.hr.salary.dao.BaseDeductionDAO;
import kr.co.wmhr.hr.salary.dao.BaseExtSalDAO;
import kr.co.wmhr.hr.salary.dao.BaseSalaryDAO;
import kr.co.wmhr.hr.salary.dao.MonthDeductionDAO;
import kr.co.wmhr.hr.salary.dao.MonthExtSalDAO;
import kr.co.wmhr.hr.salary.dao.MonthSalaryDAO;
import kr.co.wmhr.hr.salary.to.BaseDeductionTO;
import kr.co.wmhr.hr.salary.to.BaseExtSalTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;
import kr.co.wmhr.hr.salary.to.MonthSalaryTO;

public class SalaryApplicationServiceImpl implements SalaryApplicationService{
	private MonthDeductionDAO monthDeductionDAO;
	private MonthExtSalDAO monthExtSalDAO;
	private MonthSalaryDAO monthSalaryDAO;
	private BaseDeductionDAO baseDeductionDAO;
	private BaseExtSalDAO baseExtSalDAO;
	private BaseSalaryDAO baseSalaryDAO;

	public void setMonthDeductionDAO(MonthDeductionDAO monthDeductionDAO) {
		this.monthDeductionDAO = monthDeductionDAO;
	}
	public void setMonthExtSalDAO(MonthExtSalDAO monthExtSalDAO) {
		this.monthExtSalDAO = monthExtSalDAO;
	}
	public void setMonthSalaryDAO(MonthSalaryDAO monthSalaryDAO) {
		this.monthSalaryDAO = monthSalaryDAO;
	}
	public void setBaseDeductionDAO(BaseDeductionDAO baseDeductionDAO) {
		this.baseDeductionDAO = baseDeductionDAO;
	}
	public void setBaseExtSalDAO(BaseExtSalDAO baseExtSalDAO) {
		this.baseExtSalDAO = baseExtSalDAO;
	}
	public void setBaseSalaryDAO(BaseSalaryDAO baseSalaryDAO) {
		this.baseSalaryDAO = baseSalaryDAO;
	}
	@Override
	public MonthSalaryTO findMonthSalary(String applyYearMonth, String empCode) {

		HashMap<String, Object> resultMap = monthSalaryDAO.batchMonthSalaryProcess(applyYearMonth, empCode);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		MonthSalaryTO monthSalary = (MonthSalaryTO) resultMap.get("monthSalary");
		monthSalary.setMonthDeductionList(monthDeductionDAO.selectMonthDeductionList(applyYearMonth, empCode));
		monthSalary.setMonthExtSalList(monthExtSalDAO.selectMonthExtSalList(applyYearMonth, empCode));

		
		return monthSalary;
	}
	@Override
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode) {


		ArrayList<MonthSalaryTO> yearSalary = monthSalaryDAO.selectYearSalary(applyYear, empCode);

		return yearSalary;
	}
	@Override
	public void modifyMonthSalary(MonthSalaryTO monthSalary) {

		monthSalaryDAO.updateMonthSalary(monthSalary);

	}
	@Override
	public ArrayList<BaseDeductionTO> findBaseDeductionList() {


		ArrayList<BaseDeductionTO> baseDeductionList = baseDeductionDAO.selectBaseDeductionList();


		return baseDeductionList;
	}
	@Override
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList) {

		for(BaseDeductionTO baseDeduction : baseDeductionList){
			switch(baseDeduction.getStatus()){
				case "insert" :
					baseDeductionDAO.insertBaseDeduction(baseDeduction);
					break;
				case "update" :
					baseDeductionDAO.updateBaseDeduction(baseDeduction);
					break;
				case "delete" :
					baseDeductionDAO.deleteBaseDeduction(baseDeduction);
					break;
			}
		}


		
	}
	@Override
	public ArrayList<BaseSalaryTO> findBaseSalaryList() {

		
		ArrayList<BaseSalaryTO> baseSalaryList = baseSalaryDAO.selectBaseSalaryList();



		return baseSalaryList;
	}
	@Override
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList) {


		for(BaseSalaryTO baseSalary : baseSalaryList){
			if(baseSalary.getStatus().equals("update"))
				baseSalaryDAO.updateBaseSalary(baseSalary);
		}

		
	}
	@Override
	public ArrayList<BaseExtSalTO> findBaseExtSalList() {

		ArrayList<BaseExtSalTO> baseExtSalList = baseExtSalDAO.selectBaseExtSalList();


		return baseExtSalList;
	}
	@Override
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList) {


		for(BaseExtSalTO baseExtSal : baseExtSalList){
			if(baseExtSal.getStatus().equals("update"))
				baseExtSalDAO.updateBaseExtSal(baseExtSal);
		}

		
	}
}
