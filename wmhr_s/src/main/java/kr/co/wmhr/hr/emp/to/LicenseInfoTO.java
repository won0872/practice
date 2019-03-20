package kr.co.wmhr.hr.emp.to;

import kr.co.wmhr.base.to.BaseTO;

public class LicenseInfoTO extends BaseTO{
	private String empCode, licenseCode,licenseName, getDate, expireDate, licenseLevel, licenseCenter, issueNumber;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}
	
	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getLicenseLevel() {
		return licenseLevel;
	}

	public void setLicenseLevel(String licenseLevel) {
		this.licenseLevel = licenseLevel;
	}

	public String getLicenseCenter() {
		return licenseCenter;
	}

	public void setLicenseCenter(String licenseCenter) {
		this.licenseCenter = licenseCenter;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
}