package ch05;

public class Ex01 {
	public static void main(String[] args) {
		int arr[] = new int [5];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i*10;
			System.out.println("arr["+i+"]:" + arr[i]);
		}
		System.out.println("------------------------");
		int[] arr2 = {1,2,3,4,5};
		String arr3[] = {"Java", "JSP", "Oracle", "MangoDB", "Flutter", "Pytion"};
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr3[i]);
		}
		
	}
}

