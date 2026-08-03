package ch04;

public class Ex12 {

	public static void main(String[] args) {
		System.out.println("\t구구단\t");
				for (int i = 2; i<=9; i++){
					System.out.print("\t"+i+"단\t\t");
					for (int j = 1;j<=9; j++){
						System.out.print(i+"*"+j+"="+(i*j)+"\t");
					}
					System.out.println();
				}
	}

}
