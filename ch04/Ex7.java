package ch04;

public class Ex7 {
    public static void main(String[] args) {
        int sum= 0;
        for(int i=0;i<=11;i++){
            sum+=i;
        }
        System.out.println("sum: " + sum);
        int j =0;
        for(;j<5;j++){
            System.out.println("j: " + j);
        }
        for(int i =0;i<Integer.MAX_VALUE;i++){
            System.out.println(i);
            if(i ==10000000) break;
        }
        for(;;){
            System.out.println("무한반복");
        }
        //int k = 10;
    }
}
