package kr.co.wmhr.hr.emp.applicationService;

import java.util.ArrayList;

import kr.co.wmhr.base.applicationService.BaseApplicationService;
import kr.co.wmhr.base.dao.DeptDAO;
import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.hr.emp.dao.CareerInfoDAO;
import kr.co.wmhr.hr.emp.dao.EducationInfoDAO;
import kr.co.wmhr.hr.emp.dao.EmpDAO;
import kr.co.wmhr.hr.emp.dao.FamilyInfoDAO;
import kr.co.wmhr.hr.emp.dao.LicenseInfoDAO;
import kr.co.wmhr.hr.emp.dao.WorkInfoDAO;
import kr.co.wmhr.hr.emp.to.CareerInfoTO;
import kr.co.wmhr.hr.emp.to.EducationInfoTO;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.emp.to.FamilyInfoTO;
import kr.co.wmhr.hr.emp.to.LicenseInfoTO;
import kr.co.wmhr.hr.emp.to.WorkInfoTO;
import kr.co.wmhr.hr.salary.applicationService.SalaryApplicationService;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class EmpApplicationServiceImpl implements EmpApplicationService {
	
	SalaryApplicationService salaryApplicationService;
	BaseApplicationService baseApplicationService;
	
	public void setSalaryApplicationService(SalaryApplicationService salaryApplicationService) {
		this.salaryApplicationService = salaryApplicationService;
	}



	public void setBaseApplicationService(BaseApplicationService baseApplicationService) {
		this.baseApplicationService = baseApplicationService;
	}

	private DeptDAO deptDAO;
	private EmpDAO empDAO;
	private WorkInfoDAO workInfoDAO;
	private FamilyInfoDAO familyInfoDAO;
	private CareerInfoDAO careerInfoDAO;
	private LicenseInfoDAO licenseInfoDAO;
	private EducationInfoDAO educationInfoDAO;
	
	
	

	public void setSalaryApplication(SalaryApplicationService salaryApplicationService) {
		this.salaryApplicationService = salaryApplicationService;
	}



	public void setDeptDAO(DeptDAO deptDAO) {
		this.deptDAO = deptDAO;
	}

	public void setEmpDAO(EmpDAO empDAO) {
		this.empDAO = empDAO;
	}

	public void setWorkInfoDAO(WorkInfoDAO workInfoDAO) {
		this.workInfoDAO = workInfoDAO;
	}

	public void setFamilyInfoDAO(FamilyInfoDAO familyInfoDAO) {
		this.familyInfoDAO = familyInfoDAO;
	}

	public void setCareerInfoDAO(CareerInfoDAO careerInfoDAO) {
		this.careerInfoDAO = careerInfoDAO;
	}

	public void setLicenseInfoDAO(LicenseInfoDAO licenseInfoDAO) {
		this.licenseInfoDAO = licenseInfoDAO;
	}

	public void setEducationInfoDAO(EducationInfoDAO educationInfoDAO) {
		this.educationInfoDAO = educationInfoDAO;
	}

	@Override
	public void registEmployee(EmpTO emp) {
		
		
			empDAO.registEmployee(emp);
			baseApplicationService.registEmpCode(emp);
		
		

		
	}

	@Override
	public EmpTO findEmployee(String empCode) {
		
		EmpTO empTO = empDAO.selectEmp(empCode);
		
		return empTO;
	}

	@Override
	public EmpTO findAllEmployeeInfo(String empCode) {
		
		EmpTO empTO = empDAO.selectEmployee(empCode);

		ArrayList<WorkInfoTO> workInfoList = workInfoDAO.selectWorkList(empCode);
		ArrayList<FamilyInfoTO> familyInfoList = familyInfoDAO.selectFamilyList(empCode);
		ArrayList<CareerInfoTO> careerInfoList = careerInfoDAO.selectCareerList(empCode);
		ArrayList<EducationInfoTO> educationInfoList = educationInfoDAO.selectEducationList(empCode);
		ArrayList<LicenseInfoTO> licenseInfoList = licenseInfoDAO.selectLicenseList(empCode);
		empTO.setWorkInfoList(workInfoList);
		empTO.setFamilyInfoList(familyInfoList);
		empTO.setCareerInfoList(careerInfoList);
		empTO.setEducationInfoList(educationInfoList);
		empTO.setLicenseInfoList(licenseInfoList);

		
		return empTO;
	}

	@Override
	public ArrayList<EmpTO> findEmployeeListByDept(String value) {
		// TODO Auto-generated method stub
		ArrayList<EmpTO> empList = null;
		String empCode = null;
		

		if (value.equals("전체부서")) {
			empList = empDAO.selectEmpList();
		} else if (value.substring(value.length()-1, value.length()).equals("팀")) {
			empList = empDAO.selectEmpListD(value);
		} else {
			empList = empDAO.selectEmpListN(value);

			empCode = getEmpCode(value);
			workInfoDAO.selectWorkList(empCode);
		}

		
		return empList;
	}

	public String getEmpCode(String name) {
		
		String empCode = empDAO.getEmpCode(name);

		
		return empCode;
	}

	@Override
	public ArrayList<DeptTO> findDeptList() {
		System.out.println("3");
		ArrayList<DeptTO> deptList = deptDAO.selectDeptList();
		
		return deptList;
	}

	@Override
	public void modifyEmployee(EmpTO emp) {

		
		if (emp.getStatus().equals("update")) {
			empDAO.updateEmployee(emp);
		}
		
		if (emp.getWorkInfoList() != null) {
			ArrayList<WorkInfoTO> workInfoList = emp.getWorkInfoList();
			for (WorkInfoTO workInfo : workInfoList) {
				
				switch (workInfo.getStatus()) {
				case "insert":
					workInfoDAO.insertWorkInfo(workInfo);
					break;
				case "update":
					workInfoDAO.updateWorkInfo(workInfo);
					break;
				case "delete":
					workInfoDAO.deleteWorkInfo(workInfo);
					break;
				}
			}
		}
		if (emp.getFamilyInfoList() != null && emp.getFamilyInfoList().size() > 0) {
			ArrayList<FamilyInfoTO> familyInfoList = emp.getFamilyInfoList();
			for (FamilyInfoTO familyInfo : familyInfoList) {
				switch (familyInfo.getStatus()) {
				case "insert":
					familyInfoDAO.insertFamilyInfo(familyInfo);
					break;
				case "update":
					familyInfoDAO.updateFamilyInfo(familyInfo);
					break;
				case "delete":
					familyInfoDAO.deleteFamilyInfo(familyInfo);
					break;
				}
			}
		}
		
		if (emp.getEducationInfoList() != null && emp.getEducationInfoList().size() > 0) {
			ArrayList<EducationInfoTO> educationInfoList = emp.getEducationInfoList();
			for (EducationInfoTO educationInfo : educationInfoList) {
				switch (educationInfo.getStatus()) {
				case "insert":
					educationInfoDAO.insertEducationInfo(educationInfo);
					break;
				case "update":
					educationInfoDAO.updateEducationInfo(educationInfo);
					break;
				case "delete":
					educationInfoDAO.deleteEducationInfo(educationInfo);
					break;
				}
			}
		}
		
		
		if (emp.getLicenseInfoList() != null && emp.getLicenseInfoList().size() > 0) {
			ArrayList<LicenseInfoTO> licenseInfoList = emp.getLicenseInfoList();
			for (LicenseInfoTO licenseInfo : licenseInfoList) {
				switch (licenseInfo.getStatus()) {
				case "insert":
					licenseInfoDAO.insertLicenseInfo(licenseInfo);
					break;
				case "update":
					licenseInfoDAO.updateLicenseInfo(licenseInfo);
					break;
				case "delete":
					licenseInfoDAO.deleteLicenseInfo(licenseInfo);
					break;
				}
			}
		}
		
		
		if (emp.getCareerInfoList() != null && emp.getCareerInfoList().size() > 0) {
			ArrayList<CareerInfoTO> careerInfoList = emp.getCareerInfoList();
			for (CareerInfoTO careerInfo : careerInfoList) {
				switch (careerInfo.getStatus()) {
				case "insert":
					careerInfoDAO.insertCareerInfo(careerInfo);
					break;
				case "update":
					careerInfoDAO.updateCareerInfo(careerInfo);
					break;
				case "delete":
					careerInfoDAO.deleteCareerInfo(careerInfo);
					break;
				}
			}
		}

		
	}

	@Override
	public void modifyEmployeeList(ArrayList<EmpTO> empList) {
		
		for (EmpTO emp : empList) {
			modifyEmployee(emp);
		}
		
	}

	@Override
	public void deleteEmpList(ArrayList<EmpTO> empList) {
		
		   
		  for(EmpTO emp : empList){
			  empDAO.deleteEmployee(emp);
			  baseApplicationService.deleteEmpCode(emp);
		  }
		 
	}

	@Override
	public String findLastEmpCode() {
		
		String empCode = empDAO.selectLastEmpCode();
		
		return empCode;
	}

	@Override
	public EmpTO selectEmp(String name) {
		
		EmpTO empto = empDAO.selectEmp(name);
		
		return empto;
	}

	@Override
	public ArrayList<EmpTO> findWorkInfoListByDept(String code) {
		ArrayList<EmpTO> empList = new ArrayList<EmpTO>();
		ArrayList<WorkInfoTO> wlist = null;
		EmpTO empto = new EmpTO();
		
		wlist = workInfoDAO.selectWorkList(code);
		empto.setWorkInfoList(wlist);
		empList.add(empto);
		

		return empList;
	}

	@Override
	public void registDept(DeptTO deptto) {
		
		deptDAO.registDept(deptto);
		
		
		
	}

	@Override
	public void deleteDept(DeptTO deptto) {
		
		deptDAO.deleteDept(deptto);
		
		
		
	}

	@Override
	public ArrayList<BaseSalaryTO> selectPositionList() {
		
		ArrayList<BaseSalaryTO> positionList = salaryApplicationService.findBaseSalaryList();
		
		
		
		return positionList;
	}



}