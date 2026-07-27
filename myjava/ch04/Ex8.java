package ch04;

import java.util.Scanner;

class Prom{
    public void p01(){
        int sum = 0;
        for(int i=1;i<=10;i++){
            sum += i;
            System.out.print((i!=10) ? i + " + " : i + " = " + sum + "\n");
        }
    }

    public void p02(){
        int sum = 0;
        for(int i =1;i<=50;i++){
            if(i % 10 == 3 || i % 10 == 6 || i % 10 == 9){
                sum += i;
            }
            else if(i / 10 == 3 || i / 10 == 6 || i / 10 == 9){
                sum += i;
            }
        }
        System.out.println("sum = " + sum);
    }

    public void p03(int num){
        int i =1;
        int result = 0;
        while(i != 200){
            int sum = 0;
            int k = i;
            for(int j = 10;k!=0;){
                sum += k%10;
                k /= j;
            }

            if(sum == num){result += i;}
            i++;
        }

        System.out.println("sum = " + result);
    }
}

public class Ex8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Prom s = new Prom();

        System.out.print("숫자 쓰시오 : ");
        int num = sc.nextInt();

        s.p01();
        s.p02();
        s.p03(num);
    }
}
