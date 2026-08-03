package ch03;

public class Ex07 {

	public static void main(String[] args) {
		//1.배열 선언
		int arr[];//변수명 앞에도 옴
		//2.배열 크기 할당
		arr = new int[3];
		//3.배열 값을 할당
		arr[0] = 1;
		arr[1] = 2;
		arr[2] = 3;
		System.out.println(arr[0]+arr[1]+arr[2]);
		int arr3[]= new int [5];
		//배열은 내부적으로 Arrays 객체 생성 (new)이 되고 length는 배열의 길이 변수 필드
		//배열은 한번 만들어지면 크기는 불변
		int sum2 =0;
		//배열 밑에 항상 for문 있다.
		for (int i = 0; i < arr3.length; i++) {
			arr3[i] = (i+1)*10;
			sum2+=arr3[i];
		}
		System.out.println("sum2:"+sum2);
		//배열 선언과 동시에 값을 할당
		String subject[] = {"Java", "JSP", "Flutter", "Spring"};
		for (int i = 0; i < subject.length; i++) {
			System.out.print(subject[i]+"\t");
		}
	}
}
