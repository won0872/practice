package kr.co.wmhr.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wmhr.base.sf.BaseServiceFacade;
import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;

public class DeptListController extends MultiActionController {
	private BaseServiceFacade baseServiceFacade;
	private EmpServiceFacade empServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	public ModelAndView batchDeptProcess(HttpServletRequest request, HttpServletResponse response) {

		String sendData = request.getParameter("sendData");

		Gson gson = new Gson();
		ArrayList<DeptTO> deptto = gson.fromJson(sendData, new TypeToken<ArrayList<DeptTO>>() {}.getType());


		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			baseServiceFacade.batchDeptProcess(deptto);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", request.getParameter("deptName") + " 부서가 등록/삭제가 완료되었습니다.");

		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());

		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;


	}

	public ModelAndView findDeptList(HttpServletRequest request, HttpServletResponse response) {
		

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");

			System.out.println("1");
			List<DeptTO> list = empServiceFacade.findDeptList();
			DeptTO emptyBean = new DeptTO();
			
			
			modelMap.put("emptyBean", emptyBean);
			modelMap.put("list", list);
			System.out.println("5");

		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		System.out.println("6");
		return modelAndView;
	}

}
