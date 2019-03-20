package kr.co.wmhr.base.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class EmpLogoutController extends MultiActionController {


	public ModelAndView empLogout(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		String viewName="redirect:main.html";
		try{
			request.getSession().invalidate();
		}catch(Exception e){
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			viewName="error";
		}
		ModelAndView modelAndView=new ModelAndView(viewName,map);
		return modelAndView;
	}
}

