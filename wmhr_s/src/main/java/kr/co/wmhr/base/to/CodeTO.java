package kr.co.wmhr.base.to;

public class CodeTO extends BaseTO{
	String codeNumber,codeName,modifiable;

	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getModifiable() {
		return modifiable;
	}

	public void setModifiable(String modifiable) {
		this.modifiable = modifiable;
	}
}