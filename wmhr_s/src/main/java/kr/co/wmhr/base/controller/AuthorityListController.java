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
import kr.co.wmhr.base.to.AuthorityTO;
import kr.co.wmhr.hr.salary.to.BaseSalaryTO;

public class AuthorityListController extends MultiActionController{
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	
	public ModelAndView findAuthorityList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			String deptCode = request.getParameter("dept");
			String positionCode = request.getParameter("position");
			
			ArrayList<AuthorityTO> authorityList = baseServiceFacade.findAuthorityList(deptCode,positionCode);
			AuthorityTO authorityTO = new AuthorityTO();
			modelMap.put("authorityList", authorityList);
			modelMap.put("emptyBean", authorityTO);
			modelMap.put("errorCode",0);
			modelMap.put("errorMsg","success");
			
			
		} catch (Exception e) {
			modelMap.clear();
			modelMap.put("errorMsg", e.getMessage());
			
		} 
		
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
}

public ModelAndView modifyAuthority(HttpServletRequest request, HttpServletResponse response){
	String sendData = request.getParameter("sendData");
	
	try{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		
	    ArrayList<AuthorityTO> AuthorityList = gson.fromJson(sendData, new TypeToken<ArrayList<AuthorityTO>>(){}.getType());
		
		baseServiceFacade.modifyAuthority(AuthorityList);
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
