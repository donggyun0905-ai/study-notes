package ch06;

//동일한 패키지 내에는 대소문자가 같은 클래스 선언을 하면 안됨 ← 윈도우는 파일이 대소문자 구별을 하지 않기 때문!
class A{}

class a{}

//클래스 선언 : 객체를 만드는 틀
//클래스 구성 : 필드 , 메소드

class Car1{
    //필드 : 객체의 속성 <- 무조건 () 없다. 소문자 시작, 카멜표기법
    //참조형은 값이 없는 상태 ,null 기본형의 모든 타입은 기본값 o
    String carName;
    int velocity;
    String carColor;

    //메소드(methods): 객체의 기능(동작) <- 무조건 ().소문자 시작, 카멜표기법
    //SUN 제공되는 메소드 패턴 : 동사 + 명사 EX)setBackground, getColor,isEmpty
    void speedUp(){
        velocity++; //객체가 반복적으로 하는 기능을 만드는 메소드, 당연히 객체의 속성을 접근가능.
    }
    void speedDown(){
        velocity--;
        if(velocity<0)
            velocity = 0;
    }
    void stop(){
        velocity = 0;
    }
}

//class Frame{}

//파일명은 반드시 main 있는 클래스 명으로 선언을 해야하고 다른 클래스(car1) public 붙이면 안됨
public class CarEx1 {
    public static void main(String[] args) {
        //클래스는 왜 만들지? 객체를 생성하려고 함 -> 필드와 메소드를 사용하려고
        Car1/*레퍼런스 변수*/ c1 = new/*객체생성 키워드*/ Car1();
        Car1 c2 = new Car1();
        System.out.println("그랜저");
        System.out.println("은색");
        c1.speedUp(); //메소드 호출
        System.out.println(c1.carName);
        System.out.println(c1.velocity);
        System.out.println(c1.carColor);
    }
}
