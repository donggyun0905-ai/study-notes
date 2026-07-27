package ch06;
class Constructor1 {
    /*t객체를 생성하는ㄴ 아주특별한 메서드.
    반드시 클래스명과 도일한 이름으로 선언, 메ㅗ스 리턴타입 선언 자체가 없다.
    JVMdl 이 컴파일 시험에 생성자가 한나라도 생성되지 않았으면
    디폴트 생성자 (매개변수 x, 기능 x)를 추가하여 컴파일
     */
    Constructor1() {
        System.out.println("디폴트 생성자");
    }
    Constructor1(int a) {
        System.out.println("매개변수 int 형");
    }
    Constructor1(String a) {
        System.out.println("매개변수 String형");
    }
}
public class ConstructorEx1 {
    public static void main(String[] args) {
        Constructor1 c1 = new Constructor1();
        Constructor1 c2 = new Constructor1(22);
        Constructor1 c3 = new Constructor1("대한민국");
        //String 생성자는 15개 존재 <- 다양한 상황에서
        String str = new String();
    }
}
