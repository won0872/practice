package kr.co.wmhr.hr.salary.to;

import kr.co.wmhr.base.to.BaseTO;

public class BaseExtSalTO  extends BaseTO{
	private String extSalCode,
	extSalName,
	ratio;

	public String getExtSalCode() {
		return extSalCode;
	}

	public void setExtSalCode(String extSalCode) {
		this.extSalCode = extSalCode;
	}

	public String getExtSalName() {
		return extSalName;
	}

	public void setExtSalName(String extSalName) {
		this.extSalName = extSalName;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
}
