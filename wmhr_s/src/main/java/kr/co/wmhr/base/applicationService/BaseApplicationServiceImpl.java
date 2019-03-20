package kr.co.wmhr.base.applicationService;

import java.util.ArrayList;
import java.util.List;

import kr.co.wmhr.base.dao.AuthorityDAO;
import kr.co.wmhr.base.dao.CodeDAO;
import kr.co.wmhr.base.dao.DeptDAO;
import kr.co.wmhr.base.dao.DetailCodeDAO;
import kr.co.wmhr.base.dao.HolidayDAO;
import kr.co.wmhr.base.dao.MenuDAO;
import kr.co.wmhr.base.exception.IdNotFoundException;
import kr.co.wmhr.base.exception.PwMissMatchException;
import kr.co.wmhr.base.to.AuthorityTO;
import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.base.to.HolidayTO;
import kr.co.wmhr.base.to.MenuTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.hr.emp.applicationService.EmpApplicationService;
import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.salary.dao.BaseSalaryDAO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class BaseApplicationServiceImpl implements BaseApplicationService {
	EmpApplicationService empApplicationService;
	
	private DeptDAO deptDAO;
	private CodeDAO codeDAO;
	private DetailCodeDAO detailCodeDAO;
	private MenuDAO menuDAO;
	private HolidayDAO holidayDAO;
	private BaseSalaryDAO baseSalaryDAO;
	private AuthorityDAO authorityDAO;


	public void setAuthorityDAO(AuthorityDAO authorityDAO) {
		this.authorityDAO = authorityDAO;
	}

	public void setBaseSalaryDAO(BaseSalaryDAO baseSalaryDAO) {
		this.baseSalaryDAO = baseSalaryDAO;
	}

	public void setEmpApplicationService(EmpApplicationService empApplicationService) {
		this.empApplicationService = empApplicationService;
	}

	

	public void setDeptDAO(DeptDAO deptDAO) {
		this.deptDAO = deptDAO;
	}

	

	public void setCodeDAO(CodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}

	public void setDetailCodeDAO(DetailCodeDAO detailCodeDAO) {
		this.detailCodeDAO = detailCodeDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public void setHolidayDAO(HolidayDAO holidayDAO) {
		this.holidayDAO = holidayDAO;
	}

	public EmpTO loginEmployee(String name, String empCode) throws IdNotFoundException, PwMissMatchException {
		
		EmpTO emp = empApplicationService.selectEmp(name); // empName으로 사원의 정보를 찾는다

		if (emp == null) {
			
			throw new DataAccessException("아이디가 존재하지 않습니다");
		} else {
			if (emp.getEmpCode().equals(empCode)) {
				return emp;
			} else {
				throw new DataAccessException("비밀번호를 확인해주세요");

			}
		}

	}

	@Override
	public void registCodeList(List<HolidayTO> holyday) {
	

		for (HolidayTO holiday : holyday) {

			switch (holiday.getStatus()) {

			case "insert":
				holidayDAO.insertCodeList(holiday);
				break;

			/*
			 * case "update" : holidayDAO.updateCodeList(holiday); break;
			 * 
			 * case "delete": holidayDAO.deleteCodeList(holiday); break;
			 */

			}

		}

	}

	public ArrayList<CodeTO> findCodeList() {
	
		ArrayList<CodeTO> codeList = codeDAO.selectCode();

		return codeList;
	}

	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype) {

		ArrayList<DetailCodeTO> detailCodeList = null;
		detailCodeList = detailCodeDAO.selectDetailCodeList(codetype);

		return detailCodeList;
	}

	public ArrayList<MenuTO> findAllMenuList() {


		ArrayList<MenuTO> menuList = menuDAO.selectMenuList();


		return menuList;
	}

	@Override
	public ArrayList<HolidayTO> findHolidayList() {

		ArrayList<HolidayTO> holidayList = holidayDAO.selectHolidayList();

		return holidayList;
	}

	@Override
	public ArrayList<MenuTO> findUserMenuList(String empCode) { // 로그인때 실행됨


		ArrayList<MenuTO> menuList = menuDAO.selectMenuList(empCode); // menulist 가져옴


		return menuList;
	}

	@Override
	public String findWeekDayCount(String startDate, String endDate) {


		String weekdayCount = holidayDAO.selectWeekDayCount(startDate, endDate);

		return weekdayCount;
	}

	@Override
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {

		ArrayList<DetailCodeTO> detailCodeList = null;
		detailCodeList = detailCodeDAO.selectDetailCodeListRest(code1, code2, code3);


		return detailCodeList;
	}

	@Override
	public void registEmpImg(String empCode, String imgExtend) {

		EmpTO emp = empApplicationService.findAllEmployeeInfo(empCode);
		if (emp == null) {
			emp = new EmpTO();
			emp.setEmpCode(empCode);
			emp.setStatus("insert");
		} else {
			emp.setStatus("update");
		}
		emp.setImgExtend(imgExtend);
		empApplicationService.modifyEmployee(emp);


	}

	@Override
	public void batchDeptProcess(ArrayList<DeptTO> deptto) {
		
		DetailCodeTO detailCodeto = new DetailCodeTO();
		
		for (DeptTO dept : deptto) {
			switch (dept.getStatus()) {

			case "insert":
				deptDAO.registDept(dept);
				detailCodeto.setDetailCodeNumber(dept.getDeptCode()); 
				detailCodeto.setDetailCodeName(dept.getDeptName());
				detailCodeto.setCodeNumber("CO-07");
				detailCodeto.setDetailCodeNameusing("Y");
				detailCodeDAO.registDetailCode(detailCodeto);
				break;
				
			case "delete": 
				deptDAO.deleteDept(dept);
				detailCodeto.setDetailCodeNumber(dept.getDeptCode()); 
				detailCodeto.setDetailCodeName(dept.getDeptName());
				detailCodeDAO.deleteDetailCode(detailCodeto);
				break;
				
			case "normal":
				break;
			}
		}
		


	}

	@Override
	public void registEmpCode(EmpTO emp) {

		DetailCodeTO detailCodeto = new DetailCodeTO();
		detailCodeto.setDetailCodeNumber(emp.getEmpCode()); 
		detailCodeto.setDetailCodeName(emp.getEmpName());
		detailCodeto.setCodeNumber("CO-17");
		detailCodeto.setDetailCodeNameusing("N");
		detailCodeDAO.registDetailCode(detailCodeto);


	}
	
	@Override
	public void deleteEmpCode(EmpTO emp) {

		DetailCodeTO detailCodeto = new DetailCodeTO();
		detailCodeto.setDetailCodeNumber(emp.getEmpCode()); 
		detailCodeto.setDetailCodeName(emp.getEmpName());
		detailCodeDAO.deleteDetailCode(detailCodeto);

	
	}

	@Override
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {

		
		if(positionList!=null && positionList.size() > 0){

			
			for(BaseSalaryTO position : positionList){
				DetailCodeTO detailCodeto = new DetailCodeTO();
				switch(position.getStatus()){				
			
				case "insert" :
					baseSalaryDAO.insertPosition(position); 
					detailCodeto.setDetailCodeNumber(position.getPositionCode()); 
					detailCodeto.setDetailCodeName(position.getPosition());
					detailCodeto.setCodeNumber("CO-04");
					detailCodeto.setDetailCodeNameusing("Y");
					detailCodeDAO.registDetailCode(detailCodeto);
					break;
				
				case "delete" :
					baseSalaryDAO.deletePosition(position);
					detailCodeto.setDetailCodeNumber(position.getPositionCode()); 
					detailCodeto.setDetailCodeName(position.getPosition());
					detailCodeDAO.deleteDetailCode(detailCodeto);
					break;
				}
			}
		}

		
	}

	@Override
	public ArrayList<AuthorityTO> selectAuthorityList(String deptCode, String positionCode) {
		
		ArrayList<AuthorityTO> authorityList = authorityDAO.selectAuthority(deptCode,positionCode);

		return authorityList;
	}

	@Override
	public void modifyAuthority(ArrayList<AuthorityTO> authorityList) {
			if(authorityList!=null && authorityList.size() > 0){

			
			for(AuthorityTO authority : authorityList){
				switch(authority.getStatus()){				
			
				case "insert" :
					authorityDAO.insertAuthority(authority); 
					break;
				
				case "delete" :
					authorityDAO.deleteAuthority(authority);
					break;
				}
			}
		}
		
	}

}