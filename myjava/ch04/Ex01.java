package ch04;

import java.util.Scanner;

public class Ex01 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//1~20사이의 숫자중에 3 6 9 이면 해당하는 숫자는 '짝'으로 표시
		while(true) {
			System.out.println("3,6,9 게임 숫자를 입력하세요:");
			int a =sc.nextInt();
			int b = a%10; //10자리를 버림
			if(b==3||b==6||b==9) {
				System.out.println("짝");
			}else {
				System.out.println();
			}
		}
	}

}
