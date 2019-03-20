package kr.co.wmhr.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wmhr.base.sf.BaseServiceFacade;
import kr.co.wmhr.base.to.HolidayTO;

public class HolidayListController extends MultiActionController {
	private BaseServiceFacade baseServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	public ModelAndView findHolidayList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<HolidayTO> holidayList = baseServiceFacade.findHolidayList();
			HolidayTO holito = new HolidayTO();
			modelMap.put("holidayList", holidayList);
			modelMap.put("emptyHoilday", holito);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		} 
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}
	
	public ModelAndView findWeekDayCount(HttpServletRequest request, HttpServletResponse response) {
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			String weekdayCount = baseServiceFacade.findWeekDayCount(startDate, endDate);
			modelMap.put("weekdayCount", weekdayCount);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}
	
	

	   public ModelAndView regitCodeList(HttpServletRequest request, HttpServletResponse response) {

	      
	      String sendData = request.getParameter("sendData");

	      try {
	         
	    	  
	    	Gson gson = new Gson();
	  		ArrayList<HolidayTO> holydayList = gson.fromJson(sendData, new TypeToken<ArrayList<HolidayTO>>() {}.getType());
	  		
	  	
	         
	         baseServiceFacade.registCodeList(holydayList);
	         
	         modelMap.put("errorMsg","success");
	         
	         modelMap.put("errorCode", 0);

	      } catch (Exception e) {
	         
	         
	         modelMap.put("errorMsg", e.getMessage());
	         
	      } 
	      modelAndView = new ModelAndView("jsonView",modelMap);
		return null;
	   }
	
	
}