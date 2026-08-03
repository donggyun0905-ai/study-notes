package ch04;

import java.util.Iterator;

public class Ex02 {
public static void main(String[] args) {
	String month = "february";
	//switch에서 case에 break가 없으면 밑에 case 가는 성질이 있다.
	switch(month) {
	case "january":
		System.out.println("1월달");
		break;
	case "february":
		System.out.println("2월달");
		break;
	case "march":
		System.out.println("3월달");
		//break;
	default: //else
		System.out.println("4월달 이후");
		
	}
	System.out.println("******");
	//switch를 if-else 변환
	//String 값에 비교는 equals 메소드 사용. 기본형 값의 비교는 ==
	if(month.equals("january")) {
		System.out.println("1월달");
	}else if(month.equals("feb")) {
		System.out.println("2월달");
}else if(month.equals("mar")) {
	System.out.println("3월달");
}else{
	System.out.println("4월 이후..");
}
}}



