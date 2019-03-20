package kr.co.wmhr.base.sf;

import java.util.ArrayList;
import java.util.List;

import kr.co.wmhr.base.applicationService.BaseApplicationService;
import kr.co.wmhr.base.exception.IdNotFoundException;
import kr.co.wmhr.base.exception.PwMissMatchException;
import kr.co.wmhr.base.to.AuthorityTO;
import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.base.to.HolidayTO;
import kr.co.wmhr.base.to.MenuTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.emp.applicationService.EmpApplicationService;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class BaseServiceFacadeImpl implements BaseServiceFacade {

	private DataSourceTransactionManager dataSourceTransactionManager;
	private BaseApplicationService baseApplicationService;
	private EmpApplicationService empApplicationService;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public void setBaseApplicationService(BaseApplicationService baseApplicationService) {
		this.baseApplicationService = baseApplicationService;
	}

	public void setEmpApplicationService(EmpApplicationService empApplicationService) {
		this.empApplicationService = empApplicationService;
	}
	


	@Override
	public ArrayList<MenuTO> findMenuList() {
	
			dataSourceTransactionManager.beginTransaction();
		
		try {
			ArrayList<MenuTO> menuto = baseApplicationService.findAllMenuList();
		
			return menuto;
		}catch(DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
	        throw e;
		}finally {
			dataSourceTransactionManager.closeConnection();
		}
		
		
		
	}

	@Override
	public ArrayList<CodeTO> findCodeList() {
		
		ArrayList<CodeTO> codeto = baseApplicationService.findCodeList();
		
		return codeto;
	}

	@Override
	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype) {
		
		ArrayList<DetailCodeTO> detailCodeto=baseApplicationService.findDetailCodeList(codetype);
	
		return detailCodeto;
	}

	@Override
	public ArrayList<HolidayTO> findHolidayList() {


	      dataSourceTransactionManager.beginTransaction();
	      try {
	         ArrayList<HolidayTO> holidayList = baseApplicationService.findHolidayList();
	      
	         return holidayList;
	      } catch (DataAccessException e){
	         dataSourceTransactionManager.rollbackTransaction();
	         throw e;
	      } finally {
	         dataSourceTransactionManager.closeConnection();
	      }
	}

	@Override
	public String findWeekDayCount(String startDate, String endDate) {
	

		dataSourceTransactionManager.beginTransaction();
		try {
			
			String weekdayCount = baseApplicationService.findWeekDayCount(startDate, endDate);

			return weekdayCount;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {

		ArrayList<DetailCodeTO> detailCodeto=baseApplicationService.findDetailCodeListRest(code1,code2,code3);
	
		return detailCodeto;
	}

	@Override
	public void registEmpImg(String empCode, String imgExtend) {
	

	      dataSourceTransactionManager.beginTransaction();
	      try {
	         baseApplicationService.registEmpImg(empCode, imgExtend);
	         dataSourceTransactionManager.commitTransaction();
	      } catch (DataAccessException e){
	         dataSourceTransactionManager.rollbackTransaction();
	         throw e;
	      } finally {
	         dataSourceTransactionManager.closeConnection();
	      }


		
	}

	@Override
	public EmpTO login(String name, String empCode) {
	      dataSourceTransactionManager.beginTransaction();
	      try {
	    	  
	    	   EmpTO empTO = baseApplicationService.loginEmployee(name, empCode);
	   
	 
	    	   dataSourceTransactionManager.commitTransaction();
	    	   
	    	   return empTO;
	         
	      } catch (DataAccessException e){
	         dataSourceTransactionManager.rollbackTransaction();
	         throw e;
	      } catch (IdNotFoundException e) {
	    	  throw new DataAccessException("아이디를 확인해주세요");
		} catch (PwMissMatchException e) {
			throw new DataAccessException("비밀번호를 확인해주세요");
		} finally {
	         dataSourceTransactionManager.closeConnection();
	      }

	      
	}
	
	   @Override
	   public void registCodeList(List<HolidayTO> holyday) {

	      
	      dataSourceTransactionManager.beginTransaction();
	      
	      try {
	         
	      baseApplicationService.registCodeList(holyday);
	      
	      dataSourceTransactionManager.commitTransaction();

	         
	      } catch (DataAccessException e){
	         
	         dataSourceTransactionManager.rollbackTransaction();
	         throw e;
	         
	      } finally {
	         
	         dataSourceTransactionManager.closeConnection();
	         
	      }
	      
	      
	   }

	@Override
	public void batchDeptProcess(ArrayList<DeptTO> deptto) {

	      dataSourceTransactionManager.beginTransaction();
	      try {
	         baseApplicationService.batchDeptProcess(deptto);
	         dataSourceTransactionManager.commitTransaction();
	      } catch (DataAccessException e){
	         dataSourceTransactionManager.rollbackTransaction();
	         throw e;
	      } finally {
	         dataSourceTransactionManager.closeConnection();
	      }

		
	}

	@Override
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {
	
		dataSourceTransactionManager.beginTransaction();
		try {
			baseApplicationService.modifyPosition(positionList);
			
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();

			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}

		
	}

	@Override
	public ArrayList<BaseSalaryTO> findPositionList() {
			dataSourceTransactionManager.beginTransaction();
			ArrayList<BaseSalaryTO> positionList = null;
			try {
				positionList = empApplicationService.selectPositionList();
				dataSourceTransactionManager.commitTransaction();
				
			}catch(DataAccessException e) {
				dataSourceTransactionManager.rollbackTransaction();
			}finally{
				dataSourceTransactionManager.closeConnection();
			}
			return positionList;
	}

	@Override
	public ArrayList<AuthorityTO> findAuthorityList(String deptCode, String positionCode) {
		dataSourceTransactionManager.beginTransaction();
		ArrayList<AuthorityTO> authorityList = null;
		try {
			authorityList = baseApplicationService.selectAuthorityList(deptCode,positionCode);
			dataSourceTransactionManager.commitTransaction();
			
		}catch(DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
		}finally{
			dataSourceTransactionManager.closeConnection();
		}
		return authorityList;
	}

	@Override
	public void modifyAuthority(ArrayList<AuthorityTO> authorityList) {
		dataSourceTransactionManager.beginTransaction();
		try {
			baseApplicationService.modifyAuthority(authorityList);
			
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();

			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}




}