package kr.co.wmhr.hr.salary.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.salary.to.BaseExtSalTO;

public interface BaseExtSalDAO {
	public ArrayList<BaseExtSalTO> selectBaseExtSalList();
	public void updateBaseExtSal(BaseExtSalTO baseExtSal);
}
