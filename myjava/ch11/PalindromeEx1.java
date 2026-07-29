package ch11;

import common.util.StringUtil;

public class PalindromeEx1 {

	public static final int MAX = 100000;

	public static void main(String[] args) {

		/*
		 * 앞에서부터 읽을 때나 뒤에서부터 읽을 때나 모양이 같은 수를 대칭수(palindrome)라고 합니다.
		 * 대칭수(palindrome)인 585는 2진수로 나타내도
		 * 1001001001가 되어 여전히 대칭수입니다.
		 */

		// 문제1.10진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (50,045,040)
		System.out.print("1번 : ");
		int sum1 = 0;
		for (int i = 1; i <= MAX; i++) {
			String s = String.valueOf(i);
			StringBuffer sb = new StringBuffer(s);
			if(s.equals(sb.reverse().toString())){
				sum1 += i;
			}
		}

		System.out.println(StringUtil.addComma(sum1));
		//----------------------------------------------------------------------------------------------------------------------------------------------------


		// 문제2.2진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (21,865,050)
		System.out.print("2번 : ");
		int sum2 = 0;
		for (int i = 1; i <= MAX; i++) {
			String s = Integer.toBinaryString(i);
			StringBuffer sb = new StringBuffer(s);
			if(s.equals(sb.reverse().toString())){
				sum2 += i;
			}
		}
		System.out.println(StringUtil.addComma(sum2));
		//----------------------------------------------------------------------------------------------------------------------------------------------------


		// 문제3.10진수과 2진수으로 모두 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (286,602)
		System.out.print("3번 : ");
		int sum3 = 0;
		for (int i = 1; i <= MAX; i++) {
			String s1 = String.valueOf(i);
			String s2 = Integer.toBinaryString(i);
			StringBuffer sb1 = new StringBuffer(s1);
			StringBuffer sb2 = new StringBuffer(s2);
			if(s1.equals(sb1.reverse().toString())&&s2.equals(sb2.reverse().toString())){
				sum3 += i;
			}
		}
		System.out.println(StringUtil.addComma(sum3));
	}
}
