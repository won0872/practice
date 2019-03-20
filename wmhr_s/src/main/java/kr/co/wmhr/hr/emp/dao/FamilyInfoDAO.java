package kr.co.wmhr.hr.emp.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.emp.to.FamilyInfoTO;

public interface FamilyInfoDAO {

	public ArrayList<FamilyInfoTO> selectFamilyList(String code);
	public void insertFamilyInfo(FamilyInfoTO familyInfo);
	public void updateFamilyInfo(FamilyInfoTO familyInfo);
	public void deleteFamilyInfo(FamilyInfoTO familyInfo);
	
}
