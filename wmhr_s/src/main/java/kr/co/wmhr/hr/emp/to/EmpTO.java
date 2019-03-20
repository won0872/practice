package kr.co.wmhr.hr.emp.to;

import java.util.ArrayList;

import kr.co.wmhr.base.to.BaseTO;

public class EmpTO extends BaseTO{
	private String empCode,empName,birthdate,gender,mobileNumber,address,
	detailAddress,postNumber,email,lastSchool,imgExtend,position,deptName;
	private ArrayList<WorkInfoTO> workInfoList;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastSchool() {
		return lastSchool;
	}
	public void setLastSchool(String lastSchool) {
		this.lastSchool = lastSchool;
	}
	public String getImgExtend() {
		return imgExtend;
	}
	public void setImgExtend(String imgExtend) {
		this.imgExtend = imgExtend;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public ArrayList<WorkInfoTO> getWorkInfoList() {
		return workInfoList;
	}
	public void setWorkInfoList(ArrayList<WorkInfoTO> workInfoList) {
		this.workInfoList = workInfoList;
	}
	public ArrayList<CareerInfoTO> getCareerInfoList() {
		return careerInfoList;
	}
	public void setCareerInfoList(ArrayList<CareerInfoTO> careerInfoList) {
		this.careerInfoList = careerInfoList;
	}
	public ArrayList<EducationInfoTO> getEducationInfoList() {
		return educationInfoList;
	}
	public void setEducationInfoList(ArrayList<EducationInfoTO> educationInfoList) {
		this.educationInfoList = educationInfoList;
	}
	public ArrayList<LicenseInfoTO> getLicenseInfoList() {
		return licenseInfoList;
	}
	public void setLicenseInfoList(ArrayList<LicenseInfoTO> licenseInfoList) {
		this.licenseInfoList = licenseInfoList;
	}
	public ArrayList<FamilyInfoTO> getFamilyInfoList() {
		return familyInfoList;
	}
	public void setFamilyInfoList(ArrayList<FamilyInfoTO> familyInfoList) {
		this.familyInfoList = familyInfoList;
	}
	private ArrayList<CareerInfoTO> careerInfoList;
	private ArrayList<EducationInfoTO> educationInfoList;
	private ArrayList<LicenseInfoTO> licenseInfoList;
	private ArrayList<FamilyInfoTO> familyInfoList;
}

