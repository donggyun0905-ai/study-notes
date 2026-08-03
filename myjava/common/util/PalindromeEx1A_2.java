package ch11;

import common.util.CommonUtil;
import common.util.StringUtil;

public class PalindromeEx1A_2 {
	
	public static final int MAX = 100000;
	
	public static void main(String[] args) {
		
		/*
		 * 앞에서부터 읽을 때나 뒤에서부터 읽을 때나 모양이 같은 수를 대칭수(palindrome)라고 합니다. 
		 * 대칭수(palindrome)인 585는 2진수로 나타내도 
		 * 1001001001가 되어 여전히 대칭수입니다.
		 */
		

		CommonUtil cu = new CommonUtil();
		// 문제1.10진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (50,045,040)
		int sum1 = 0;

		for (int i = 0; i <= MAX; i++) {
			String temp = String.valueOf(i);
			int len = temp.length();

			if (len % 2 == 0) {
				if (cu.fixLength(temp, 0, len / 2).equals(new StringBuffer(cu.fixLength(temp, len / 2, len)).reverse().toString())) {
					sum1 += i;
				}
			}else{
				if (cu.fixLength(temp, 0, len / 2).equals(new StringBuffer(cu.fixLength(temp, len / 2 + 1, len)).reverse().toString())) {
					sum1 += i;
				}
			}
		}

		System.out.print("1번 : " + StringUtil.addComma(sum1) + "\t");
		
		// 문제2.2진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (21,865,050)
		int sum2 = 0;
		for (int i = 0; i <= MAX; i++) {
			String temp = Integer.toBinaryString(i);
			int len = temp.length();

			if (len % 2 == 0) {
				if (cu.fixLength(temp, 0, len / 2).equals(new StringBuffer(cu.fixLength(temp, len / 2, len)).reverse().toString())) {
					sum2 += i;
				}
			}else{
				if (cu.fixLength(temp, 0, len / 2).equals(new StringBuffer(cu.fixLength(temp, len / 2 + 1, len)).reverse().toString())) {
					sum2 += i;
				}
			}
		}

		System.out.print("2번 : " + StringUtil.addComma(sum2) + "\t");

		// 문제3.10진수과 2진수으로 모두 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (286,602)

		int sum3 = 0;
		for (int i = 0; i <= MAX; i++) {
			String temp1 = String.valueOf(i);
			String temp2 = Integer.toBinaryString(i);
			int len1 = temp1.length();
			int len2 = temp2.length();

			boolean ispalindrome10 = false;
			boolean ispalindrome2 = false;

			if (len1 % 2 == 0) {
				if (cu.fixLength(temp1, 0, len1 / 2).equals(new StringBuffer(cu.fixLength(temp1, len1 / 2, len1)).reverse().toString())) {
					ispalindrome10 = true;
				}
			}else{
				if (cu.fixLength(temp1, 0, len1 / 2).equals(new StringBuffer(cu.fixLength(temp1, len1 / 2 + 1, len1)).reverse().toString())) {
					ispalindrome10 = true;
				}
			}

			if (len2 % 2 == 0) {
				if (cu.fixLength(temp2, 0, len2 / 2).equals(new StringBuffer(cu.fixLength(temp2, len2 / 2, len2)).reverse().toString())) {
					ispalindrome2 = true;
				}
			}else{
				if (cu.fixLength(temp2, 0, len2 / 2).equals(new StringBuffer(cu.fixLength(temp2, len2 / 2 + 1, len2)).reverse().toString())) {
					ispalindrome2 = true;
				}
			}

			if (ispalindrome10 && ispalindrome2) {
				sum3 += i;
			}
		}

		System.out.print("3번 : " + StringUtil.addComma(sum3) + "\t");

	}
}








