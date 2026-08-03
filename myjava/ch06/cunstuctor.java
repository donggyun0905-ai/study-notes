package ch06;

import java.awt.Dialog;
import java.awt.Frame;

public class cunstuctor {
	//JVM은 생성자가 하나라도 선언이 되어 있으면 default 생성자 제공안함.
	//SUN 제공되는 클래스 중에 많이는 않지만 디폴트 생성자 없는 클래스 있음
	// 결론적으로 반드시 생성자 한개는 필요함(생성자만의기능이 있음)
	cunstuctor(int a){

	}

	public static void main(String[] args) {
		//매개변수있는 생성자 선언이 되면 디폴트 생성자 지원 안해줌.
		cunstuctor c1 = new  cunstuctor(22);
		//흔하지는 않지만 SUN에서 제공되는 클래스 중에 디폴트 생성자 없는 클래스
		Dialog d= new Dialog(new Frame());
		
	}

}
