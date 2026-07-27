package ch06;

class Method2{
/*오버로딩 : 한클래스 내에 동일한 메소드 명으로 매개변수의 개수와
* 타입을 달리 선언하는것*/

    void prn(int a){
        System.out.print(a);
    }
    void prn(int a, int b){
        System.out.print(a + "\t" + b);
    }
    void prn(int a,int b, int c){
        System.out.print(a + "\t" + b + "\t" + c);
    }
    void prn(int arr[]){
        for(int i =0;i<arr.length;i++){
            System.out.print(arr[i] + "\t");
        }
    }
    void prnf(int...arr){
        for(int i =0;i<arr.length;i++){
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
    }
}

public class MethodEx2 {
    public static void main(String[] args) {
        int a = Math.max(10,20);
        double b = Math.min(3.14,5.24);

        System.out.println(1);
        System.out.println("하하");
        System.out.println(true);
        System.out.println();

        Method2 mt = new Method2();
        mt.prn(1);
        mt.prn(1,2);
        mt.prn(1,2,3);
        int arr[] = {1,2,3,4,5,6,7,8};
        mt.prn(arr);
        System.out.println("------------------------------------");
        mt.prnf(1);
        mt.prnf(1,2);
        mt.prnf(1,2,3);
        mt.prnf(1,2,3,4,5,6);

        System.out.printf("%s %d %f", "문자열",22,3.14);
    }
}
