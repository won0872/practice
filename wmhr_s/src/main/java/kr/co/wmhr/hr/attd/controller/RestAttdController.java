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
import kr.co.wmhr.hr.attd.to.RestAttdTO;

public class RestAttdController extends MultiActionController {
	private AttdServiceFacade attdServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	
	public void setAttdServiceFacade(AttdServiceFacade attdServiceFacade) {
		this.attdServiceFacade = attdServiceFacade;
	}

	public ModelAndView findRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		String empCode = request.getParameter("empCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String code = request.getParameter("code");
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdList(empCode, startDate, endDate, code);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("restAttdList", restAttdList);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView findRejectRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		String empCode = request.getParameter("empCode");
		String applyYear = request.getParameter("applyYear");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> rejectRestAttdList = attdServiceFacade.findRejectRestAttdList(empCode, applyYear);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("rejectRestAttdList", rejectRestAttdList);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView findRestAttdListByToday(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		String empCode = request.getParameter("empCode");
		String toDay = request.getParameter("toDay");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			RestAttdTO restAttdList = attdServiceFacade.findRestAttdListByToday(empCode, toDay);
			modelMap.put("restAttdList", restAttdList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView findUseAnnualLeaveList(HttpServletRequest request, HttpServletResponse response) {
		String empCode = request.getParameter("empCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> useAnnualLeaveList = attdServiceFacade.findUseAnnualLeaveList(empCode, startDate,
					endDate);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("useAnnualLeaveList", useAnnualLeaveList);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView registRestAttd(HttpServletRequest request, HttpServletResponse response) {
		String sendData = request.getParameter("sendData");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			RestAttdTO restAttd = gson.fromJson(sendData, RestAttdTO.class);
			attdServiceFacade.registRestAttd(restAttd);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView removeRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		String sendData = request.getParameter("sendData");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<RestAttdTO> restAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<RestAttdTO>>() {}.getType());
			attdServiceFacade.removeRestAttdList(restAttdList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView removeRejectRestAttdList(HttpServletRequest request, HttpServletResponse response) {
		String sendData = request.getParameter("sendData");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			ArrayList<RestAttdTO> rejectRestAttdList = gson.fromJson(sendData, new TypeToken<ArrayList<RestAttdTO>>() {}.getType());
			attdServiceFacade.removeRejectRestAttdList(rejectRestAttdList);
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
