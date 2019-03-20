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
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class PositionListController extends MultiActionController{
	
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	
	public ModelAndView findPositionList(HttpServletRequest request, HttpServletResponse response) {
		
			try {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");
				ArrayList<BaseSalaryTO> positionList = baseServiceFacade.findPositionList();
				BaseSalaryTO positionTO = new BaseSalaryTO();
				modelMap.put("positionList", positionList);
				modelMap.put("emptyPositionBean", positionTO);
				modelMap.put("errorCode",0);
				modelMap.put("errorMsg","success");
				
			} catch (Exception e) {
				modelMap.clear();
				modelMap.put("errorMsg", e.getMessage());
				
			} 
			
			modelAndView = new ModelAndView("jsonView", modelMap);
			return modelAndView;
	}
	
	public ModelAndView modifyPosition(HttpServletRequest request, HttpServletResponse response){
		String sendData = request.getParameter("sendData");
		
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			Gson gson = new Gson();
			
		    ArrayList<BaseSalaryTO> positionList = gson.fromJson(sendData, new TypeToken<ArrayList<BaseSalaryTO>>(){}.getType());
			
			baseServiceFacade.modifyPosition(positionList);
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);


		} catch (Exception e) {
			modelMap.clear();
			modelMap.put("errorMsg", e.getMessage());
			modelMap.put("errorCode", -2);
		} 

		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}
	

}
