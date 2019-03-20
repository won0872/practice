package kr.co.wmhr.hr.attd.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wmhr.hr.attd.sf.AttdServiceFacade;
import kr.co.wmhr.hr.attd.to.MonthAttdMgtTO;

public class MonthAttdManageController extends MultiActionController{
	private AttdServiceFacade attdServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	
	

	public void setAttdServiceFacade(AttdServiceFacade attdServiceFacade) {
		this.attdServiceFacade = attdServiceFacade;
	}

	public ModelAndView findMonthAttdMgtList(HttpServletRequest request, HttpServletResponse response){
		String applyYearMonth = request.getParameter("applyYearMonth");
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = attdServiceFacade.findMonthAttdMgtList(applyYearMonth);
			modelMap.put("monthAttdMgtList", monthAttdMgtList);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);
			
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView modifyMonthAttdList(HttpServletRequest request, HttpServletResponse response){
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<MonthAttdMgtTO>>(){}.getType());
			attdServiceFacade.modifyMonthAttdMgtList(monthAttdMgtList);
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