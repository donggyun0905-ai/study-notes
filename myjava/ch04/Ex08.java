package ch04;

public class Ex08 {

	public static void main(String[] args) {
		
		/*문제1.for문을 이용해서 1~10까지의 합을 구하시오.(식과 함께)
		 * 1 + 2 +.... + 10 = 55*/
		int i =0;
		int sum= 0;
		for(i=1;i<11;i++) {
			System.out.print(i);
			sum+=i;
			if(i==10) {
				System.out.print("=");
			}else {
				System.out.print("+");
			}
		}System.out.println(sum);
		
			
		/* 문제2. 1~50사이의 3, 6, 9 게임의 합은? 
		* Hint : %와 /를 사용. 33/10 => 3 
		* sum : 627
		*/
		sum= 0;
		for(i=1;i<51;i++) {
			int a = i%10;//일의 자리수 ex) 27%10 ->7
			int b = i/10;//십의 자리수 ex) 27/10 ->2
			if(a==3||a==6||a==9||b==3) {
				sum+=i;
			}
		}System.out.println("sum:"+sum);
		
		
		
		/* 문제3. for문을 이용해서 1부터 200까지의 값 중에서
		 * 각 자리 숫자의 합이 10인 숫자들의 합을 구하시오. (while)
		 * 예: 19, 28, 37...109, 118, 127...
	     * sum : 1990
		 */
		sum=0;
		int a,b,c;
		for(i=1; i<201; i++) {
			c=i/100;//백의 자리 ex) 199/100 ->1
			b=i%100/10; //십의 자리 ex) 187%100 =87 87/10 = 8
			a=i%10;//일의 자리 ex) 187%10 = 7
			
			if((a+b+c)==10) {
				sum+=i;
			}
		}System.out.println("sum:"+sum);
		
		//범위 : 1~200이고 합이 10
		System.out.println(digitSum(10,200));
		//범위 : 1~1,000이고 합이 10
		System.out.println(digitSum(10,1000));
		//범위 : 1~10,000이고 합이 15
		System.out.println(digitSum(15,10000));
	}
	
	public static int digitSum(int condition /* 범위 */, int range /* 합한 숫자 */) {
		int sum = 0;
		for (int i=1; i<range+1; i++) {
			int num = i;//자리수 계산을 위해 원본 값을 복사
			int digitSum = 0;//현재 숫자의 자리수 합
			while(num>0) {
				digitSum+=num%10;//마지막 자리수를 더함 ex)627%10 ->7
				num/=10;//627/10->62
			}
			if(digitSum==condition)
				sum+=i;
		}//for
		return sum;
	}
	
	

}
