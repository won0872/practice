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
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class BaseSalaryController extends MultiActionController{
	private SalaryServiceFacade salaryServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setSalaryServiceFacade(SalaryServiceFacade salaryServiceFacade) {
		this.salaryServiceFacade = salaryServiceFacade;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public ModelAndView findBaseSalaryList(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<BaseSalaryTO> baseSalaryList = salaryServiceFacade.findBaseSalaryList();
			modelMap.put("baseSalaryList", baseSalaryList);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView modifyBaseSalaryList(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		String sendData = request.getParameter("sendData");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<BaseSalaryTO> baseSalaryList = gson.fromJson(sendData, new TypeToken<ArrayList<BaseSalaryTO>>(){}.getType());
			salaryServiceFacade.modifyBaseSalaryList(baseSalaryList);
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
