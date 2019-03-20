package kr.co.wmhr.hr.emp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.CareerInfoTO;
import kr.co.wmhr.hr.emp.to.EducationInfoTO;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.emp.to.FamilyInfoTO;
import kr.co.wmhr.hr.emp.to.LicenseInfoTO;
import kr.co.wmhr.hr.emp.to.WorkInfoTO;

public class EmpDetailController extends MultiActionController {
	private EmpServiceFacade empServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	
	
	


	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}


	public ModelAndView findAllEmployeeInfo(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String empCode = request.getParameter("empCode");
		
		
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			EmpTO empTO=empServiceFacade.findAllEmpInfo(empCode);
			modelMap.put("empBean", empTO);
			FamilyInfoTO familyInfoTO = new FamilyInfoTO();
			CareerInfoTO careerInfoTO = new CareerInfoTO();
			EducationInfoTO educationInfoTO = new EducationInfoTO();
			LicenseInfoTO licenseInfoTO = new LicenseInfoTO();
			WorkInfoTO workInfoTO = new WorkInfoTO();
			modelMap.put("emptyFamilyInfoBean",familyInfoTO );
			modelMap.put("emptyCareerInfoBean", careerInfoTO);
			modelMap.put("emptyWorkInfoBean", workInfoTO);
			modelMap.put("emptyEducationInfoBean", educationInfoTO);
			modelMap.put("emptyLicenseInfoBean", licenseInfoTO);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	
	
	public ModelAndView modifyEmployee(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String sendData = request.getParameter("sendData");
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			EmpTO emp = gson.fromJson(sendData, EmpTO.class);
			empServiceFacade.modifyEmployee(emp);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}

		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	
	public ModelAndView removeEmployeeList(HttpServletRequest request, HttpServletResponse response){
		String sendData = request.getParameter("sendData");
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
				
			Gson gson = new Gson();
			ArrayList<EmpTO> empList = gson.fromJson(sendData, new TypeToken<ArrayList<EmpTO>>(){}.getType());
			empServiceFacade.deleteEmpList(empList);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}

		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
}
