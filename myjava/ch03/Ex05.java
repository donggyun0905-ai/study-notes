package ch03;

import java.util.Scanner;

public class Ex05 {

	public static void main(String[] args) {
		/*Scanner와 % , &&, while를 사용하여 입력한 값이 3의 배수인지 메세지를 보여주세요.
		 * (일회성이 아닌 연속성 구현)*/
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("숫자를 입력하세요");
			int a = sc.nextInt();
			if(a!=0&&a%3==0) {
				System.out.println(a+"는 3의 배수입니다.");
			}else {
				System.out.println(a+"는 3의 배수가 아닙니다.ㄴ");
			}
		}
		
	}

}
