package ch11;

import common.util.StringUtil;

import java.util.Arrays;

public class PalindromeEx2 {

	public static final int MAX = 100000;

	public static void main(String[] args) {
		int sum[] = new int[3];
		for (int i = 0; i < MAX; i++) {
			if(isPalindrome(i,10)){
				sum[0] += i;
			}
			if(isPalindrome(i,2)){
				sum[1] += i;
			}
			if(isPalindrome(i,10)&&isPalindrome(i,2)){
				sum[2] += i;
			}
		}

		System.out.println(Arrays.toString(sum));
	}
	
	//대칭수를 구하는 메소드 선언. a는 비교되는 값,radix는 진수를 결정
	//StringBuffer sb 생성, 메소드 두번 호출
	public static boolean isPalindrome(int a, int radix){
		StringBuffer sb = new StringBuffer(Integer.toString(a,radix));
		return sb.toString().equals(sb.reverse().toString());
	}
}
