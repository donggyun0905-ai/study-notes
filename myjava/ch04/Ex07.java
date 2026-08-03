package ch04;

public class Ex07 {
	public static void main(String[] args) {
		//for문을 이용해서 1~10까지 합을 구하시오
		int sum=0;
		for(int i = 1; i<11; i++) {
			sum+=i;
		}System.out.println("sum"+sum);
	
		int j =0;
		for (;j<5;j++) {
			System.out.println("j:"+ j );
		}
		for (int i =0; i<Integer.MAX_VALUE; i++) {
			System.out.println(i);
			if(i==100) break;
		}int k =10;
		for(;;) {
			System.out.println("무한반복");
		}
		/* int k =10; 논리적인 에러 : 위 코드에서 계속 실행되고 있어 및에 코드까지 도달하지 못함.*/
	
	}
}
