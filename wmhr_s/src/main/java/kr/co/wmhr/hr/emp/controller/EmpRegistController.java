package kr.co.wmhr.hr.emp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.EmpTO;

public class EmpRegistController extends MultiActionController {
	private EmpServiceFacade empServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	
	
	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public void setModelMap(ModelMap modelMap) {
		this.modelMap = modelMap;
	}

	public ModelAndView registEmployee(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		try {
			EmpTO emp = new EmpTO();
			System.out.println("깨지냐"+request.getParameter("emp_name"));
			emp.setEmpName(request.getParameter("emp_name"));
			emp.setDeptName(request.getParameter("dept_name"));
			emp.setPosition(request.getParameter("position"));
			emp.setGender(request.getParameter("gender"));
			emp.setMobileNumber(request.getParameter("mobile_number"));
			emp.setEmpCode(request.getParameter("emp_code"));
			emp.setAddress(request.getParameter("address"));
			emp.setDetailAddress(request.getParameter("detail_address"));
			emp.setBirthdate(request.getParameter("birthday"));
			emp.setPostNumber(request.getParameter("post_number"));
			emp.setImgExtend(request.getParameter("img_extend"));
			emp.setLastSchool(request.getParameter("last_school"));
			emp.setEmail(request.getParameter("email"));

			empServiceFacade.registEmployee(emp);

			modelMap.put("errorMsg", request.getParameter("emp_name") + " 사원이 등록되었습니다.");

		} catch (Exception dae) {
			modelMap.put("errorMsg", "사원 등록에 실패했습니다 : " + dae.getMessage());

		}

		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView findLastEmpCode(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		try {
			String empCode = empServiceFacade.findLastEmpCode();
			modelMap.put("lastEmpCode", empCode);
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

