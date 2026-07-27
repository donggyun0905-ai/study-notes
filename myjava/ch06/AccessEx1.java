package ch06;

/*접근 제어자 : 클래스, 필드, 메소드
* 1.private : 1%
* 2.protected : 0.1%
* 3.public : 99%
* 4.선언을 안하는 경우 : friendly
* */

import java.awt.*;

class Access1{
    public int a;//어디에서나 접근 가능
    protected  int b;//상속 or 같은 패키지 가능
    //sun제공하는 클래스, 필드, 메소드 사용하기 윟나 현실적인 방법은 상속이 유일
    private int c;//자신의 클래스 안에서만 가능, 데이터 보호 목적

    private int speed;

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
        if(speed<0)
            speed = 0;
    }
}

class Access2 extends Frame{
    void prn(){
        String str = paramString();
        System.out.println(str);
    }
}

public class AccessEx1 {
    public static void main(String[] args) {
        Access1 ac = new Access1();
        ac.setSpeed(-10);
        System.out.println(ac.getSpeed());

        //Math mt = new Math();
        Frame f = new Frame();

        //String str = f.paramString();
        Access2 ac2 = new Access2();
        ac2.prn();
    }
}
