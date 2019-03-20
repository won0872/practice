package kr.co.wmhr.hr.emp.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.emp.to.CareerInfoTO;

public interface CareerInfoDAO {
	public ArrayList<CareerInfoTO> selectCareerList(String code);
	public void insertCareerInfo(CareerInfoTO careerInfo);
	public void updateCareerInfo(CareerInfoTO careerInfo);
	public void deleteCareerInfo(CareerInfoTO careerInfo);
}

