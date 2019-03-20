package kr.co.wmhr.hr.emp.applicationService;

import java.util.ArrayList;

import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public interface EmpApplicationService {
	public void registEmployee(EmpTO emp);
	public EmpTO findEmployee(String empCode);
	public EmpTO findAllEmployeeInfo(String empCode);
	public ArrayList<EmpTO> findEmployeeListByDept(String deptName);
	public ArrayList<EmpTO> findWorkInfoListByDept(String code);
	public ArrayList<DeptTO> findDeptList();
	public void modifyEmployee(EmpTO emp);
	public void modifyEmployeeList(ArrayList<EmpTO> empList);
	public void deleteEmpList(ArrayList<EmpTO> empList);

	public String getEmpCode(String name);
	
	public String findLastEmpCode();
	public EmpTO selectEmp(String name);
	
	public void registDept(DeptTO deptto);
	public void deleteDept(DeptTO deptto);
	public ArrayList<BaseSalaryTO> selectPositionList();
}
