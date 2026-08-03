package ch04;

public class Ex10_1 {

	public static void main(String[] args) {
		
		/* 1. for와 break를 사용하여 1에서 n까지의 합이 100 이상 중 가장 가까운 합 */
		int sum = 0;
		int n = 0;
		
		for (int i = 1; true; i++) {
			sum += i;
			if (sum >= 100) { // 100 이상이 되는 순간
				n = i;
				break;        // 루프 탈출
			}
		}
		System.out.println("100 이상이 되는 n: " + n + ", sum: " + sum);
		System.out.println("------------------------------------------");


		/* 2. for 문을 이용하여 1에서 n까지의 합이 100 이하 중 가장 가까운 n과 sum */
		sum = 0;
		n = 0;
		
		for (int i = 1; true; i++) {
			sum += i;
			if (sum > 100) {   // 100을 넘어가면
				sum -= i;      // 방금 더한 값(i)을 빼서 100 이하로 되돌림
				n = i - 1;     // 직전의 n값 저장
				break;
			}
		}
		System.out.println("100 이하 중 가장 가까운 n: " + n + ", sum: " + sum);
		System.out.println("------------------------------------------");


		/* 3. 1~10 사이의 짝수를 출력하시오 */
		System.out.print("1~10 짝수: ");
		for (int i = 1; i <= 10; i++) {
			if (i % 2 == 0) {
				System.out.print(i + "\t");
			}
		}
		System.out.println("\n------------------------------------------");


		/* 4. 1~10 사이의 짝수를 출력하시오 (continue 사용) */
		System.out.print("1~10 짝수(continue): ");
		for (int i = 1; i <= 10; i++) {
			if (i % 2 != 0) {
				continue; // 홀수는 건너뜀
			}
			System.out.print(i + "\t");
		}
		System.out.println("\n------------------------------------------");


		/* 5. 1~20 사이에 3의 배수의 식과 합을 출력하시오 (continue)
		 * 출력 결과: 3 + 6 + 9 + 12 + 15 + 18 = 63 */
		sum = 0;
		for (int i = 1; i <= 20; i++) {
			if (i % 3 != 0) {
				continue; // 3의 배수가 아니면 건너뜀
			}
			
			// 첫 번째 3이 아닐 때만 숫 앞에 ' + '를 출력
			if (i > 3) {
				System.out.print(" + ");
			}
			
			sum += i;
			System.out.print(i);
		}
		System.out.println(" = " + sum);
	}
}