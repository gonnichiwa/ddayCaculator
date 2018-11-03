package ddaymain;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableInstant;

import java.util.ArrayList;

/**
 * DdayListCaculator
 *
 * Example 1
 * UserSetDateTime : 2018-10-03, TodayDate : 2018-11-03 일 때
 * list (오늘 날짜 기준으로 Dday 10일 단위, 1년 단위로 나타냄
 * |D+40|2018년11월12일
 * |D+50|2018년11월22일
 * |D+60|2018년12월2일
 * |D+70|2018년12월12일
 * |D+80|2018년12월22일
 * |D+90|2019년1월1일
 * |D+100|2019년1월11일
 * |D+110|2019년1월21일
 *
 * Example 2
 * UserSetDateTime : 2018-11-05, TodayDate : 2018-11-03 일 때
 * |D-60|2018년11월6일
 * |D-50|2018년11월16일
 * |D-40|2018년11월26일
 * |D-30|2018년12월6일
 * |D-20|2018년12월16일
 * |D-10|2018년12월26일
 * |DDAY!|2019년1월5일
 * |D+10|2019년1월15일
 * |D+20|2019년1월25일
 * |D+30|2019년2월4일
 * |D+40|2019년2월14일
 * |D+50|2019년2월24일
 *
 * */
public class DdayListCaculator {

	private static int Dday = 0;

	private static DateTime now;
	private static DateTime nowDt;
	private static DateTime setDt;
	private static MutableDateTime changingNowDt;
	private static MutableDateTime changingSetDt;

	private static ArrayList<DaysInfo> ddayFilteredList = new ArrayList<DaysInfo>();
	private static final int LIST_SIZE = 20;

	public static void main(String[] args){
		now = new DateTime();
		nowDt = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(),0,0,0);
		setDt = new DateTime(2019,1,5,0,0,0);

		changingNowDt = nowDt.toMutableDateTime();
		changingSetDt = setDt.toMutableDateTime();
		
		String nowDateStr = changingNowDt.getYear() + "-" + changingNowDt.getMonthOfYear() + "-" + changingNowDt.getDayOfMonth();
		String setDateStr = changingSetDt.getYear() + "-" + changingSetDt.getMonthOfYear() + "-" + changingSetDt.getDayOfMonth();

		System.out.println("nowDateStr:" + nowDateStr);
		System.out.println("setDateStr:" + setDateStr);

		Dday = Days.daysBetween(setDt, nowDt).getDays();

		System.out.println("dday:" + Dday);
		System.out.println("=============");

		initList();

		System.out.println("=============");

		printList();
	}

	private static void initList() {
		while(ddayFilteredList.size() < LIST_SIZE){
			if(isNowDateFirst()){
				addListRow(changingNowDt, setDt);
				changingNowDt.addDays(1);
				continue;
			}

			if(isDDateFirst()){
				addListRow(changingNowDt, setDt);
				changingNowDt.addDays(1);
				continue;
			}

			// dday (오늘기준변동날짜가 setDt와 맞을 경우)
			if (isDday()) {
				addListRow(changingSetDt, setDt);
				changingNowDt.addDays(1);
			}


		}
	}// main

	// 조건 충족 시 리스트 row 추가 (1년단위, 10일 단위)
	private static void addListRow(MutableDateTime changingDate, ReadableInstant standardDate) {
		if((getDays(changingDate, standardDate) % 365 == 0) ||
				(getDays(changingDate, standardDate) % 10 == 0) ||
				(getDays(changingDate, standardDate) == 0)){
			DaysInfo info = new DaysInfo(getDdayString(changingDate, standardDate), getPatternDate(changingDate));
			ddayFilteredList.add(info);
			System.out.println(info.toString());
		}
	}

	private static String getDdayString(MutableDateTime changingDate, ReadableInstant standardDate) {
		int days = getDays(changingDate, standardDate);
		System.out.println("list insert! days="+days);
		if(isDDateFirst()){
			return "D+"+Math.abs(days);
		} else if(isNowDateFirst()){
			return "D-"+Math.abs(days);
		} else {
			return "DDAY!";
		}
	}

	private static String getPatternDate(MutableDateTime changingDate) {
		return changingDate.getYear()+"년"+changingDate.getMonthOfYear()+"월"+changingDate.getDayOfMonth()+"일";
	}
	private static int getDays(ReadableInstant changingDate, ReadableInstant standardDate) {
		return Days.daysBetween(changingDate,standardDate).getDays();
	}
	private static boolean isDDateFirst(){
		return Days.daysBetween(changingSetDt, changingNowDt).getDays() > 0;
	}
	private static boolean isNowDateFirst() {
		return Days.daysBetween(changingSetDt, changingNowDt).getDays() < 0;
	}
	private static boolean isDday() {
		return Days.daysBetween(changingSetDt, changingNowDt).getDays() == 0;
	}
	private static int getDdayFromChangingSettedDate(){
		return Days.daysBetween(changingSetDt, nowDt).getDays();
	}
	private static int getDdayFromChangingNowDate(){
		return Days.daysBetween(setDt, changingNowDt).getDays();
	}
	private static void printList() {
		for(DaysInfo info : ddayFilteredList){
			System.out.println("|"+info.getDday()+"|"+info.getDdayMonth());
		}
	}

}// class
