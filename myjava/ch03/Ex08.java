package ch03;

/*Path 설정
 * 1. C:\Java\JDK-24\bin 경로 복사
 * 2. 시스템 속성 > 환경 변수 path 위에 경로 추가
 * 3. 다시 cmd에서 java --version 확인
 */

//컴파일 (javac.exe): javac Test.java -(성공)-> Test.class 자동 생성
//실행(Java.exe): Java Test

public class Ex08 {
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			
		}
		System.out.println("종료");
	}

}
