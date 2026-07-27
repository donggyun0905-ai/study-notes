package ch02;
//클래스명은 반드시 첫 번째 철자는대문자
public class Ex1 {
    public static void main(String[] args){
    //변수 선언
        int a;
        a=10;
        int b = 10;
        double c = 3.14;
        String str = "java";
        System.out.println(a);
        System.out.println(str);

        int x,y,z;
        x = 10;
        //integer's maximum values print
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);

        //Ex2 클래스를 객체 생성
        //JVM(자바가상머신-실행엔진)이 Ex2.class를 가지고 와서 메모리 객체를 생성
        Ex2 ex = new Ex2();
    }
}
