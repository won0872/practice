package kr.co.wmhr.hr.salary.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;

import kr.co.wmhr.hr.salary.sf.SalaryServiceFacade;
import kr.co.wmhr.hr.salary.to.MonthSalaryTO;

public class MonthSalaryController extends MultiActionController{
	private SalaryServiceFacade salaryServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();


	public void setSalaryServiceFacade(SalaryServiceFacade salaryServiceFacade) {
		this.salaryServiceFacade = salaryServiceFacade;
	}

	public ModelAndView findMonthSalary(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String applyYearMonth = request.getParameter("applyYearMonth");
		String empCode = request.getParameter("empCode");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			MonthSalaryTO monthSalary = salaryServiceFacade.findMonthSalary(applyYearMonth,empCode);
			request.setAttribute("monthSalaryTO", monthSalary);
			modelMap.put("monthSalary", monthSalary);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	
	public ModelAndView findYearSalary(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String applyYear = request.getParameter("applyYear");
		String empCode = request.getParameter("empCode");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<MonthSalaryTO> yearSalary = salaryServiceFacade.findYearSalary(applyYear, empCode);
			modelMap.put("yearSalary", yearSalary);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView modifyMonthSalary(HttpServletRequest request, HttpServletResponse response){
		String sendData = request.getParameter("sendData");

		try {	
			Gson gson = new Gson();
			MonthSalaryTO monthSalary = gson.fromJson(sendData, MonthSalaryTO.class);
			salaryServiceFacade.modifyMonthSalary(monthSalary);
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
