package kr.co.wmhr.hr.emp.sf;

import java.util.ArrayList;

import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.hr.emp.applicationService.EmpApplicationService;
import kr.co.wmhr.hr.emp.to.EmpTO;

public class EmpServiceFacadeImpl implements EmpServiceFacade {
	private EmpApplicationService empApplicationService;

	public void setEmpApplicationService(EmpApplicationService empApplicationService) {
		this.empApplicationService = empApplicationService;
	}


	public EmpTO getEmp(String name) {

		EmpTO empto = null;
			empto = empApplicationService.selectEmp(name);
		return empto;
	}

	@Override
	public ArrayList<EmpTO> findEmpList(String value) {
					ArrayList<EmpTO> empList = empApplicationService.findEmployeeListByDept(value);
					return empList;
	}
	
	public ArrayList<EmpTO> workInfoList(String code) {
			ArrayList<EmpTO> empList = empApplicationService.findWorkInfoListByDept(code);
			return empList;
	}
	
	@Override
	public void registEmployee(EmpTO empto) {
			empApplicationService.registEmployee(empto);
	}

	@Override
	public ArrayList<DeptTO> findDeptList() {
		System.out.println("2");
		ArrayList<DeptTO> deptList = empApplicationService.findDeptList();
		return deptList;
	}

	@Override
	public String findLastEmpCode() {
			String empCode = empApplicationService.findLastEmpCode();
			return empCode;
	}

	@Override
	public EmpTO findAllEmpInfo(String empCode) {
			EmpTO empTO = empApplicationService.findAllEmployeeInfo(empCode);
			return empTO;
	}

	@Override
	public void modifyEmployee(EmpTO emp) {
			empApplicationService.modifyEmployee(emp);
	}

	@Override
	public void deleteEmpList(ArrayList<EmpTO> empList) {
					empApplicationService.deleteEmpList(empList);
	}

	@Override
	public void registDept(DeptTO deptto) {
			empApplicationService.registDept(deptto);
	}

	@Override
	public void deleteDept(DeptTO deptto) {
			empApplicationService.deleteDept(deptto);
	}

}
