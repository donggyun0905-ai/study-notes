package ch04;

public class Ex10 {
    public static void main(String[] args) {
        int sum=0;
        for (int i =0;true;i++){
            if(sum>100){
                System.out.println("i: " + (i-1));
                break;
            }
            sum += i;
        }
        System.out.println("sum : " + sum);

        sum = 0;
        int j =0;
        for(int i =0;true;i++){
            sum += i;
            if(sum > 100){
                sum -= i;
                j = i-1;
                break;
            }
        }
        System.out.println("j: "+ j);
        System.out.println("sum: " + sum);

        for(int i =1;i<11;i++){
            if(i%2==0)
                continue;
            System.out.println(i+"\t");
        }
        System.out.println();

        for(int i =0;i<21;i++){
            if(i%3 != 0)
                continue;
            if(i>3)
                System.out.println(" + ");
            sum += i;
            System.out.println(i);
        }
        System.out.println(" = " + sum);
    }
}
