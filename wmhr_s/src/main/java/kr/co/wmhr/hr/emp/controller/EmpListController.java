package kr.co.wmhr.hr.emp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.EmpTO;

public class EmpListController extends MultiActionController {
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	private EmpServiceFacade empServiceFacade;

	
	
	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public void setModelMap(ModelMap modelMap) {
		this.modelMap = modelMap;
	}

	public ModelAndView emplist(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			String value = "전체부서";

			if (request.getParameter("value") != null)
				value = (String) request.getParameter("value");

			ArrayList<EmpTO> list = empServiceFacade.findEmpList(value);
			

			modelMap.put("list", list);

		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());

		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView workInfoList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			String code = request.getParameter("code");


			ArrayList<EmpTO> list = empServiceFacade.workInfoList(code);
			modelMap.put("list", list);
		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());

		}

		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

}