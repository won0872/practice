package kr.co.wmhr.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import kr.co.wmhr.base.sf.BaseServiceFacade;
import kr.co.wmhr.base.to.MenuTO;

public class MenuController extends AbstractController {
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public ModelAndView menulist(HttpServletRequest request, HttpServletResponse response) {


		try {

			ArrayList<MenuTO> menuList = baseServiceFacade.findMenuList();
			modelMap.put("menuList", menuList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (Exception dae) {
			modelMap.clear();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", dae.getMessage());

		}
		
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}
	
	public ModelAndView findUserMenuList(HttpServletRequest request, HttpServletResponse response){

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			/*String empCode = request.getParameter("empCode");*/
			/*ArrayList<MenuTO> userMenuList=baseServiceFacade.findUserMenuList(empCode);*/
			/*modelMap.put("userMenuList", userMenuList);*/
			modelMap.put("errorMsg","success");
			modelMap.put("errorCode", 0);

		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", "Show list failed");
		}
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}
	
	

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		menulist(request, response);
		return null;
	}
}
