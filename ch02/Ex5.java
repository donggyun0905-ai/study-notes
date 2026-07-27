package ch02;
import java.util.Locale;

public class Ex5 {
    public static void main(String[] args) {
     //data type : 기본형(8) + 참조형 -> sun제공 + 외부lib + 본인의 만든 클래스
        Ex4 ex4 = new Ex4();
        String str = new String();

        //String: new 연산자 없이 유일하게 객체를 만들 수 있는 클래스 -> 너무 사용 많이함
        String str1 = "fwerfwefw";
        String str2 = new String ("etwefwefw");

        System.out.println(str1.length());
        System.out.println(str2.toUpperCase());

        //math class : pi,올림,내림,반올림,절대값,sin,cos,tan
        //반지름5인 원의 넓이를 구하시오.
        System.out.println(Math.PI*5*5);
        System.out.println(Math.abs(-10));
    }
}
