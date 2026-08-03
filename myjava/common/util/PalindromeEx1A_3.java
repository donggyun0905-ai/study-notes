package common.util;

import common.util.StringUtil;

public class PalindromeEx1A_3 {

	public static final int MAX = 100000;

	public static void main(String[] args) {
		
		/*
		 * 앞에서부터 읽을 때나 뒤에서부터 읽을 때나 모양이 같은 수를 대칭수(palindrome)라고 합니다. 
		 * 대칭수(palindrome)인 585는 2진수로 나타내도 
		 * 1001001001가 되어 여전히 대칭수입니다.
		 */
		
		// 문제1.10진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (50,045,040)
		System.out.print("1번 : ");
		int a, b, c, d, e, f;
		int n1, n2, n3, n4, n5;

		for (int i = 0; i <= 100000; i++) {
		    // 1. 각 자리수 추출 연산
		    a  = i % 10;       // 일의 자리
		    n1 = i / 10;

		    b  = n1 % 10;      // 십의 자리
		    n2 = n1 / 10;

		    c  = n2 % 10;      // 백의 자리
		    n3 = n2 / 10;

		    d  = n3 % 10;      // 천의 자리
		    n4 = n3 / 10;

		    e  = n4 % 10;      // 만의 자리
		    n5 = n4 / 10;

		    f  = n5 % 10;
		}

		// 문제2.2진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (21,865,050)
		System.out.print("2번 : ");

		// 문제3.10진수과 2진수으로 모두 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (286,602)
		System.out.print("3번 : ");

	}
}
