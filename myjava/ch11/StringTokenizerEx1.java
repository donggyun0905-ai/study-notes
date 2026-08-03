package ch11;

import java.util.StringTokenizer;

public class StringTokenizerEx1 {
	public static void main(String[] args) {
		//토큰: 문자열을 의미 있는 값으로 슬라이싱하는것
		String str1 = "자바 JSP Flutter Spring";
		StringTokenizer st1 = new StringTokenizer(str1);
		int len1 = st1.countTokens();
		System.out.println(len1);
		while(st1.hasMoreElements()) {
			System.out.println(st1.nextToken());
		}
		System.out.println("------------");
		String str2 = "민지;하니:다니엘;해란:혜인";
		StringTokenizer st2 = new StringTokenizer(str2, ";:");
		int len2 = st2.countTokens();
		System.out.println(len2);
		String arr2[] = new String[len2];
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = st2.nextToken();
			System.out.println(arr2[i]);
			
		}//for
		System.out.println("-----------");
		String str3 = "민지;하니:다니엘;해란:혜인";
		String arr3[] = str3.split(";");
		for (int i = 0; i < arr3.length; i++) {
			System.out.println(arr3[i]);
			
		}
				
				
	}
}
