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
import kr.co.wmhr.hr.attd.to.DayAttdTO;

public class DayAttdController extends MultiActionController {
	private AttdServiceFacade attdServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setAttdServiceFacade(AttdServiceFacade attdServiceFacade) {
		this.attdServiceFacade = attdServiceFacade;
	}

	

	public ModelAndView findDayAttdList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		String applyDay = request.getParameter("applyDay");
		String empCode = request.getParameter("empCode");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<DayAttdTO> dayAttdList = attdServiceFacade.findDayAttdList(empCode, applyDay);
			modelMap.put("dayAttdList", dayAttdList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView registDayAttd(HttpServletRequest request, HttpServletResponse response) {
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			DayAttdTO dayAttd = gson.fromJson(sendData, DayAttdTO.class);
			attdServiceFacade.registDayAttd(dayAttd);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView removeDayAttdList(HttpServletRequest request, HttpServletResponse response) {
		
		String sendData = request.getParameter("sendData");
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<DayAttdTO> dayAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<DayAttdTO>>() {}.getType());
			attdServiceFacade.removeDayAttdList(dayAttdList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
}

