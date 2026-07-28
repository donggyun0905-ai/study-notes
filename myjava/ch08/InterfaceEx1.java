package ch08;

interface Calc{
    //추상메소드 : abstract는 사용하지 않아도 자동으로 선언됨
    void plus(int a, int b);
}

class Function extends Object implements Calc{
    @Override
    public void plus(int a, int b) {
        System.out.println("기능에 맞는 plus 메소드");
    }
}

class Graphics implements Calc{
    @Override
    public void plus(int a, int b) {
        System.out.println("그래픽에 맞는 plus 메소드");
    }
}

public class InterfaceEx1 {
    public static void main(String[] args) {
        Calc c;//타입으로만 선언 가능
        //c = new Calc(); //추상클래스와 동일하게 객체 생성은 불가
        c = new Function();
        c.plus(1,2);
        c = new Graphics();
        c.plus(3,4);
    }
}
