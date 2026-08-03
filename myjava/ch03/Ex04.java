package ch03;

public class Ex04 {

	public static void main(String[] args) {
		//논리 연산자: &, &&, |, ||, ^
		//&(and): 홍길동은 키도 크고 잘생겼다: 두 가지 조건 모두 만족
		//&&는 이미 false면 거기서 false로 끝남. 하지만 &는 끝까지 다 판단
		//&는 전체 조건에 상관없이 무조건 실행, &&는 전체 조건에 영향을 미치는 값이 있으면 뒤에는 실행 자체가 안됨
		// |(or): 미팅남은 키가 크거나 또는 잘생겼다: 한 가지 조건만 만족
		int a= 10 , b = 4, c= 0;
		System.out.println((a==b)&(a>b));
		System.out.println((a==b)&&(a>b));
		//System.out.println((a==b)&(a/c==b));
		System.out.println((a==b)&&(a/c==b));
		System.out.println("**************");
		System.out.println((a!=b)|(a<b));//true
		System.out.println((a!=b)||(a<b));//true
		System.out.println("**************");
		//XOR(^): 배타적 논리연산 <- 서로 값이 다르면 true
		System.out.println(true^false);//f
		System.out.println(true^true);//t
		System.out.println(false^false);//t
		System.out.println(false^true);//f
		
		
	}

}
