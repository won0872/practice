package kr.co.wmhr.hr.salary.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wmhr.hr.salary.sf.SalaryServiceFacade;
import kr.co.wmhr.hr.salary.to.BaseExtSalTO;

public class BaseExtSalController extends MultiActionController{
	private SalaryServiceFacade salaryServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();



	public void setSalaryServiceFacade(SalaryServiceFacade salaryServiceFacade) {
		this.salaryServiceFacade = salaryServiceFacade;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public ModelAndView findBaseExtSalList(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<BaseExtSalTO> baseExtSalList = salaryServiceFacade.findBaseExtSalList();
			modelMap.put("baseExtSalList", baseExtSalList);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView modifyBaseExtSalList(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String sendData = request.getParameter("sendData");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<BaseExtSalTO> baseExtSalList = gson.fromJson(sendData, new TypeToken<ArrayList<BaseExtSalTO>>(){}.getType());
			salaryServiceFacade.modifyBaseExtSalList(baseExtSalList);
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
