package kr.co.wmhr.hr.emp.dao;

import java.util.ArrayList;

import kr.co.wmhr.hr.emp.to.EmpTO;

public interface EmpDAO {
	public EmpTO selectEmp(String empno);
	public ArrayList<EmpTO> selectEmpList();
	public ArrayList<EmpTO> selectEmpListD(String dept);
	public String selectLastEmpCode();
	public ArrayList<EmpTO> selectEmpListN(String name);
	public String getEmpCode(String name);
	public EmpTO selectEmployee(String empCode);
	
	public void registEmployee(EmpTO empto);
	public void updateEmployee(EmpTO emp);
	public void deleteEmployee(EmpTO emp);
}
