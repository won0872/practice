package kr.co.wmhr.base.sf;

import java.util.ArrayList;
import java.util.List;

import kr.co.wmhr.base.to.AuthorityTO;
import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.base.to.HolidayTO;
import kr.co.wmhr.base.to.MenuTO;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public interface BaseServiceFacade {
	public ArrayList<MenuTO> findMenuList();
	public ArrayList<CodeTO> findCodeList();
	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype);
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1,String code2,String code3);
	public ArrayList<HolidayTO> findHolidayList();
	public String findWeekDayCount(String startDate, String endDate);
	public void registEmpImg(String empCode, String imgExtend);
	public EmpTO login(String name, String empCode);
	public void registCodeList(List<HolidayTO> holyday);
	public void batchDeptProcess(ArrayList<DeptTO> deptto);
	
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList);
	public ArrayList<BaseSalaryTO> findPositionList();
	
	public ArrayList<AuthorityTO> findAuthorityList(String deptCode, String positionCode);
	public void modifyAuthority(ArrayList<AuthorityTO> authorityList);
}