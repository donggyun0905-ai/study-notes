package ch04;

public class GuGuDan {
    public static void main(String[] args) {
        for(int i =1;i<=9;i++){
            System.out.print(i + "단");
            for(int j =1;j<=9;j++){
                System.out.println("\t" + i + " x " + j + " = " + (i*j));
            }
            System.out.println();
        }
    }
}
