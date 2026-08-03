package ch04;

import java.util.Scanner;

public class Ex05 {

	public static void main(String[] args) {
		// 제어문 중첩 가능
		// 입력된 숫자가 양수, 음수, 홀수,짝수를 체크하시오.
		Scanner sc = new Scanner(System.in);
		System.out.print("숫자를 입력하세요:");
		int a = sc.nextInt();
		if (a > 0) {
			if (a % 2 == 0) {
				System.out.println(a + "는 양수이고 짝수입니다.");
			} else {
				System.out.println(a + "는 양수이고 홀수입니다.");
			}
		} else if (a < 0) {
			System.out.println(a + "는 음수입니다.");
		} else {
			System.out.println("0입니다.");
		}
	}
}
