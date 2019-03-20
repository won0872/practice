package kr.co.wmhr.base.to;

public class HolidayTO extends BaseTO{
	private String applyDay, holidayName, note;

	public String getApplyDay() {
		return applyDay;
	}

	public void setApplyDay(String applyDay) {
		this.applyDay = applyDay;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
