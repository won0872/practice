package kr.co.wmhr.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.wmhr.base.sf.BaseServiceFacade;
import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.common.exception.DataAccessException;

public class CodeListController extends MultiActionController {
	private BaseServiceFacade baseServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	public ModelAndView detailCodelist(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<DetailCodeTO> detailCodeList = baseServiceFacade.findDetailCodeList(code);

			modelMap.put("detailCodeList", detailCodeList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (Exception dae) {
			modelMap.clear();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", dae.getMessage());
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;

	}

	public ModelAndView codelist(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<CodeTO> codeList = baseServiceFacade.findCodeList();
			modelMap.put("codeList", codeList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);

		} catch (DataAccessException dae) {
			modelMap.clear();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView detailCodelistRest(HttpServletRequest request, HttpServletResponse response) {
		String code1 = request.getParameter("code1");
		String code2 = request.getParameter("code2");
		String code3 = request.getParameter("code3");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<DetailCodeTO> detailCodeList = baseServiceFacade.findDetailCodeListRest(code1, code2, code3);
			modelMap.put("detailCodeList", detailCodeList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);


		} catch (DataAccessException dae) {
			modelMap.clear();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", dae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;

	}

}
