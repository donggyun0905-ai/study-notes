package ch06;

/*Object클래스 : 최상위 클래스로서 9개의 메소드 가지고 있는 객체
* 물체(객체) > 생물 > 동물 > 사람 > 남자>차은우;
* 차은우
* */
class Super3/*2대 클래스*/ extends Object/*1대클래스*/ {
    int a;

    Super3(){
        super();
        System.out.println("Super3 생성자");
    }

    void m(){}
}

class Sub3/*3대 클래스*/ extends Super3/*2대 클래스부터는 생략 안됨*/{
    Sub3(){
        super();//상위클래스 생성호출 생략, 반드시 첫 번째
        System.out.println("Sub3 생성자");
    }
}

public class ConstructorEx3 {
    public static void main(String[] args) {
        Super3 s1 = new Super3();
        Sub3 s2 = new Sub3();

    }
}
