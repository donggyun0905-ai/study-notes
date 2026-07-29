package ch11;

import common.util.StringUtil;

import java.util.Arrays;

public class PalindromeEx3 {

	public static final int MAX = 100000;

	public static void main(String[] args) {
		int sum[] = new int[3];
		for (int i = 1; i <= MAX; i++) {
			boolean dec= isPalindrome(i+"");
			boolean bin = isPalindrome(Integer.toBinaryString(i));

			if(dec) sum[0] += i;
			if(bin) sum[1] += i;
			if(dec && bin) sum[2] += i;
		}
		for (int i = 0; i < sum.length; i++) {
			System.out.println(StringUtil.addComma(sum[i]));
		}
	}
	
	//객체 생성 없이 메모리 효율적 관리
	public static boolean isPalindrome(String s){
		for (int i = 0; i < s.length()/2; i++) {
			if(s.charAt(i) != s.charAt(s.length()-1-i)){
				return false;
			}
		}
		return true;
	}
}
