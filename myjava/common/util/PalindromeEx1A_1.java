package ch11;

public class PalindromeEx1A_1 {
	
	public static final int MAX = 100000;
	
	public static boolean isPalindrome(String str) {
		return str.equals(new StringBuilder(str).reverse().toString());
	}
	
	public static void main(String[] args) {
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		
		/*
		 * 앞에서부터 읽을 때나 뒤에서부터 읽을 때나 모양이 같은 수를 대칭수(palindrome)라고 합니다. 
		 * 대칭수(palindrome)인 585는 2진수로 나타내도 
		 * 1001001001가 되어 여전히 대칭수입니다.
		 */
		for (int i = 1; i <= MAX; i++) {
			boolean decimal = isPalindrome(String.valueOf(i));
			boolean binary = isPalindrome(Integer.toBinaryString(i));
			// 1
			if (decimal) {
				sum1 += i;
			}
			// 2
			if (binary) {
				sum2 += i;
			}
			// 3
			if(decimal && binary) {
				sum3 += i;
			}
		}
		
		// 문제1.10진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (50,045,040)
		System.out.println("1번 : " + sum1);
		
		// 문제2.2진수의 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (21,865,050)
		System.out.println("2번 : " + sum2);

		// 문제3.10진수과 2진수으로 모두 대칭수인 100,000 이하 숫자의 합은 얼마입니까? (286,602)
		System.out.println("3번 : " + sum3);

	}
}








