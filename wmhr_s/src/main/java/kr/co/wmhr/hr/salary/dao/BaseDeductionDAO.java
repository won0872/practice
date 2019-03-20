package kr.co.wmhr.hr.salary.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.salary.to.BaseDeductionTO;

public interface BaseDeductionDAO {
	public ArrayList<BaseDeductionTO> selectBaseDeductionList();
	public void insertBaseDeduction(BaseDeductionTO baseDeduction);
	public void updateBaseDeduction(BaseDeductionTO baseDeduction);
	public void deleteBaseDeduction(BaseDeductionTO baseDeduction);
}
