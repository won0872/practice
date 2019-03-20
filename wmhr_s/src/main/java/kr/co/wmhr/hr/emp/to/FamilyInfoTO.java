package kr.co.wmhr.hr.emp.to;

import kr.co.wmhr.base.to.BaseTO;

public class FamilyInfoTO extends BaseTO{
	private String empCode,familyCode,familyName,relation,birthdate,liveTogether;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFamilyCode() {
		return familyCode;
	}

	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getLiveTogether() {
		return liveTogether;
	}

	public void setLiveTogether(String liveTogether) {
		this.liveTogether = liveTogether;
	}
}