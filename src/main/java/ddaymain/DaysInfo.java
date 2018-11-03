package ddaymain;

public class DaysInfo {

	private String dday;
	private String ddayMonth;

	public DaysInfo(String inputDdayStr, String ddayMonthStr){
		this.dday = inputDdayStr;
		this.ddayMonth = ddayMonthStr;
	}

	public String getDday(){
		return dday;
	}

	public void setDday(String dday){
		this.dday = dday;
	}

	public String getDdayMonth(){
		return ddayMonth;
	}

	public void setDdayMonth(String ddayPattern){
		this.ddayMonth = ddayPattern;
	}

	@Override
	public String toString() {
		return getDday() + "|" + getDdayMonth();
	}
}
