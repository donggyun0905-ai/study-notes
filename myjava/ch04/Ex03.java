package ch04;

class A3_03 {

}

public class Ex03 {

	public static void main(String[] args) {
		// Data Type : 기본형 + 참조형 (클래스)
		int i = 10;
		int j = i;// call by value
		i = 11;

		A3_03 a = new A3_03();
		A3_03 b = new A3_03();
		System.out.println(a);
		System.out.println(b);
		a = b;//call by reference
		// 참조형에서 ==은 객체 주소 값 비교
		System.out.println(a == b);
	}

}
