package ch03;

public class Ex02_1 {

	public static void main(String[] args) {
		//Ex02를 응용하여 모든 한글을 출력하시오(hint: 가~힣)
		//한 행에 20자 출력 (while 안에 for문 특별한 조건에 return)
		
		char c= 'ㄱ';
		while(true) {
			for (int i = 0; i<20; i++) {
				System.out.print((c++)+"\t");
				if(c=='힣'+2) {
					return;
				}//if
			}//for
			System.out.println();
		}//while
	}
}
