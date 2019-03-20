package kr.co.wmhr.hr.emp.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.emp.to.EducationInfoTO;

public interface EducationInfoDAO {
	public ArrayList<EducationInfoTO> selectEducationList(String code);
	public void insertEducationInfo(EducationInfoTO educationInfo);
	public void updateEducationInfo(EducationInfoTO educationInfo);
	public void deleteEducationInfo(EducationInfoTO educationInfo);
}

