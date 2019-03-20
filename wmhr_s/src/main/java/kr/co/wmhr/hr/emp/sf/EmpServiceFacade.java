package kr.co.wmhr.hr.emp.sf;

import java.util.ArrayList;

import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.hr.emp.to.EmpTO;

public interface EmpServiceFacade {
	public ArrayList<EmpTO> findEmpList(String dept);
	public void registEmployee(EmpTO empto);
	public ArrayList<DeptTO> findDeptList();
	public ArrayList<EmpTO> workInfoList(String code);
	public EmpTO findAllEmpInfo(String empCode);
	public void modifyEmployee(EmpTO emp);
	public void deleteEmpList(ArrayList<EmpTO> empList);
	public EmpTO getEmp(String name);
	public String findLastEmpCode();
	
	/*public DeptTO findDept(String dept);*/
	public void registDept(DeptTO deptto);
	public void deleteDept(DeptTO deptto);
}
