package kr.co.wmhr.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kr.co.wmhr.base.to.PdfTO;
import kr.co.wmhr.hr.emp.sf.EmpServiceFacade;
import kr.co.wmhr.hr.emp.to.EmpTO;
import kr.co.wmhr.hr.emp.to.WorkInfoTO;
import kr.co.wmhr.hr.salary.to.MonthDeductionTO;
import kr.co.wmhr.hr.salary.to.MonthSalaryTO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EmpReportController extends MultiActionController {
	private EmpServiceFacade empServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();

	public void setEmpServiceFacade(EmpServiceFacade empServiceFacade) {
		this.empServiceFacade = empServiceFacade;
	}

	public ModelAndView findEmpReport(HttpServletRequest request, HttpServletResponse response) {

		try {
			

			String empCode = request.getParameter("empCode");
			String format = request.getParameter("format");

			EmpTO empto = empServiceFacade.findAllEmpInfo(empCode);
			ArrayList<WorkInfoTO> workInfoTOList = empto.getWorkInfoList();

			/* employment */
			PdfTO empReport = new PdfTO();
			empReport.setEmpName(empto.getEmpName());
			empReport.setPosition(empto.getPosition());
			empReport.setAddress(empto.getAddress());
			empReport.setDetailAddress(empto.getDetailAddress());
			empReport.setDeptName(empto.getDeptName());
			
			
			for(WorkInfoTO workinfoTO:workInfoTOList) {
				empReport.setHiredate(workinfoTO.getHiredate());
			}
			

			ArrayList<PdfTO> empReportList = new ArrayList<PdfTO>();
			empReportList.add(empReport);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(empReportList);
			modelMap.put("source", source);
			modelMap.put("format", format);

			if (format.equals("pdf")) {
				modelAndView = new ModelAndView("pdfView", modelMap);
			} else if (format.equals("xls")) {
				modelAndView = new ModelAndView("xlsView", modelMap);
			}

		} catch (Exception error) {
			error.printStackTrace();
		}

		return modelAndView;
	}
	
	public ModelAndView requestMonthSalary(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			MonthSalaryTO monthSalaryto = (MonthSalaryTO) request.getAttribute("monthSalary");

			String empCode = request.getParameter("empCode");
			String format = request.getParameter("format");
			String applyMonth = request.getParameter("applyMonth");
			
			EmpTO empto = empServiceFacade.findAllEmpInfo(empCode);
			ArrayList<WorkInfoTO> workInfoTOList = empto.getWorkInfoList();

			/* employment */
			PdfTO empReport = new PdfTO();
			empReport.setEmpName(empto.getEmpName());
			empReport.setPosition(empto.getPosition());
			empReport.setAddress(empto.getAddress());
			empReport.setDetailAddress(empto.getDetailAddress());
			empReport.setDeptName(empto.getDeptName());
			
			/*SalaryStatement*/
			empReport.setApplyYearMonth(monthSalaryto.getApplyYearMonth());
			empReport.setSalary(monthSalaryto.getSalary());
			empReport.setTotalExtSal(monthSalaryto.getTotalExtSal());
			empReport.setTotalDeduction(monthSalaryto.getTotalDeduction());
			empReport.setTotalPayment(monthSalaryto.getTotalPayment());
			empReport.setRealSalray(monthSalaryto.getRealSalary());
			empReport.setCost(monthSalaryto.getCost());
			empReport.setApplyMonth(applyMonth);
			
			
			for(WorkInfoTO workinfoTO:workInfoTOList) {
				empReport.setHiredate(workinfoTO.getHiredate());
			}
			

			ArrayList<PdfTO> empReportList = new ArrayList<PdfTO>();
			empReportList.add(empReport);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(empReportList);
			modelMap.put("source", source);
			modelMap.put("format", format); 
			
			if (format.equals("pdf")) {
				modelAndView = new ModelAndView("pdfView", modelMap);
			} else if (format.equals("xls")) {
				modelAndView = new ModelAndView("xlsView", modelMap);
			}

		} catch (Exception error) {
			error.printStackTrace();
		}

		return modelAndView;
	}
}
