package ch03;

public class Ex02 {
	public static void main(String[] args) {
		//++연산자를 사용하여 알파벳 대문자 소문자 모두 출력
		char c ='a';
		char c1 =c++;
		System.out.println(c+":"+ c1);
		char c2='A';
		for(int i = 0; i<26; i++) {
			System.out.print((c2++)+ "\t"); // \t tab 공간 
		}
		
		System.out.println();
		char c3= 'a';
		for(int i= 0; i<26; i++) {
			System.out.print((c3++)+"\t");
		}
	}
}
