package kr.co.wmhr.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.wmhr.base.sf.BaseServiceFacade;
import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.EmpTO;

public class EmpLoginController extends MultiActionController {
	
	private EmpServiceFacade empServiceFacade;
	private BaseServiceFacade baseServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();


	
	
	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}



	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	
	
	
	public ModelAndView empLogin(HttpServletRequest request, HttpServletResponse response) {
		String viewName="loginform";
		try{
			// TODO Auto-generated method stub
			String name=request.getParameter("name");
			String ec=request.getParameter("ec");
			
			EmpTO empto = baseServiceFacade.login(name, ec);
			if(empto != null){
				
				request.getSession().setAttribute("id", name);
				request.getSession().setAttribute("dept", empto.getDeptName());
				request.getSession().setAttribute("position", empto.getPosition());
				request.getSession().setAttribute("code", empto.getEmpCode());
				viewName="redirect:main.html";
				
			}
			
		}catch(Exception e){
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());
		}
		
		modelAndView=new ModelAndView(viewName, modelMap);
		return modelAndView;
	}
}
