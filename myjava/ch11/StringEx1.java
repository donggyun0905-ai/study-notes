package ch11;

public class StringEx1 {
    public static void main(String[] args) {
        int a = 10,b=10;
        System.out.println(a==b);
        System.out.println("------------");
        String s1 = new String("Java");
        String s2 = new String("Java");
        System.out.println(s1 ==s2);//false
        System.out.println(s1.equals(s2));//true
        System.out.println("------------");
        /*String은 new 연산자 없이 객체를 생성하는 유일한 클래스
        * new 연산자 없이 선언된 값들은 String 저장소 선언
        * 새로운 값을 선언을 할때 동일한 값이 있는지 없는지 검사를 해서 있다면 재사용 없으면 새롭게 만듬
        * 만약 s3+"World" 하면 기존에 "java"는 그대로 값이 유지되고 새로운 문자열 javaworld가 선언됨
        * 왜냐면 다른 변수가 "Java"를 사용. 이런것이 약점이고 보완를 한 클래스 String Buffer임
        * */
        String s3 = "Java";
        String s4 = "Java";
        System.out.println(s3==s4);//true
        System.out.println(s3.equals(s4));//true
        System.out.println("--------------");
        System.out.println(s3.hashCode());
        s3 = s3+"World";
        System.out.println(s3.hashCode());
        String s5 = "apple";
        String s6 = "APPLE";
        System.out.println(s5.equalsIgnoreCase(s6));//대소문자 무시하고 같으지 검색
    }
}
